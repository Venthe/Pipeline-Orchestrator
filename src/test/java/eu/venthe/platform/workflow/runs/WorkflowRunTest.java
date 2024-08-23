package eu.venthe.platform.workflow.runs;

import eu.venthe.platform.organization.domain.OrganizationName;
import eu.venthe.platform.repository.domain.RepositoryName;
import eu.venthe.platform.shared_kernel.dynamic_variable.DynamicProperty;
import eu.venthe.platform.shared_kernel.dynamic_variable.TextDynamicProperty;
import eu.venthe.platform.workflow.data_interpolation.Expression;
import eu.venthe.platform.workflow.runs.dependencies.TimeService;
import eu.venthe.platform.workflow.runs.events.WorkflowRunCreatedEvent;
import lombok.NonNull;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.nio.file.Path;
import java.time.*;
import java.util.Collections;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Stream;

class WorkflowRunTest {
    private static final Map<String, DynamicProperty> EMPTY_INPUTS = Collections.emptyMap();
    private static final String WHITESPACE = " ";
    private static final OrganizationName EXAMPLE_ORGANIZATION_NAME = new OrganizationName("Example-Organization");
    private static final RepositoryName EXAMPLE_REPOSITORY_NAME = new RepositoryName("Example-Repository");
    private static final OffsetDateTime EXAMPLE_DATE = OffsetDateTime.of(LocalDate.of(2024, Month.APRIL, 1), LocalTime.of(13, 13), ZoneOffset.ofHours(1));
    private static final String EXAMPLE_RUN_NAME = "Example run name";

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    TimeService timeService;

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    WorkflowRun.WorkflowData workflowData;

    @Mock
    WorkflowRun.EventData eventData;

    @Test
    void should_set_the_creation_date_when_created() {
        // Given
        Mockito.when(timeService.offset().now()).thenReturn(EXAMPLE_DATE);

        // When
        var run = WorkflowRun.crate(workflowData, eventData, EMPTY_INPUTS, timeService).getRight();

        // Then
        Assertions.assertThat(run.getStartDate()).isEqualTo(EXAMPLE_DATE);
    }

    @Test
    void should_provide_event_when_created() {
        // When
        var result = WorkflowRun.crate(workflowData, eventData, EMPTY_INPUTS, timeService);
        var events = result.getLeft();
        var run = result.getRight();

        // Then
        Assertions.assertThat(events)
                .containsExactlyInAnyOrder(
                        WorkflowRunCreatedEvent.builder()
                                .organizationName(EXAMPLE_ORGANIZATION_NAME)
                                .repositoryName(EXAMPLE_REPOSITORY_NAME)
                                .workflowRunId(run.getId())
                                .build()
                );
    }

    @Nested
    class Name {

        @Test
        void should_provide_path_as_name_when_workflow_name_is_not_provided() {
            // Given
            Mockito.when(workflowData.workflowDefinition().getName()).thenReturn(null);
            var workflowPath = Path.of(".pipeline/workflows/example.yml");
            Mockito.when(workflowData.path()).thenReturn(workflowPath);

            // When
            var run = WorkflowRun.crate(workflowData, eventData, EMPTY_INPUTS, timeService).getRight();

            // Then
            Assertions.assertThat(run.getName()).isEqualTo(workflowPath.toString());
        }

        @Test
        void should_provide_path_as_name_when_workflow_name_is_not_provided() {
            // Given
            Mockito.when(workflowData.workflowDefinition().getName()).thenReturn(WHITESPACE);
            var workflowPath = Path.of(".pipeline/workflows/example.yml");
            Mockito.when(workflowData.path()).thenReturn(workflowPath);

            // When
            var run = WorkflowRun.crate(workflowData, eventData, EMPTY_INPUTS, timeService).getRight();

            // Then
            Assertions.assertThat(run.getName()).isEqualTo(workflowPath.toString());
        }

        @Test
        void should_provide_name_from_workflow_when_workflow_name_is_provided() {
            // Given
            var workflowName = "Example";
            Mockito.when(workflowData.workflowDefinition().getName()).thenReturn(workflowName);

            // When
            var run = WorkflowRun.crate(workflowData, eventData, EMPTY_INPUTS, timeService).getRight();

            // Then
            Assertions.assertThat(run.getName()).isEqualTo(workflowName);
        }
    }

    @Nested
    class RunName {

        private static final Runnable NO_OP = () -> {
        };

        @Test
        void should_display_event_specific_information_when_run_name_is_omitted() {
            // Given
            Mockito.when(workflowData.workflowDefinition().getRunName()).thenReturn(null);
            Mockito.when(eventData.runName()).thenReturn(EXAMPLE_RUN_NAME);

            // When
            var run = WorkflowRun.crate(workflowData, eventData, EMPTY_INPUTS, timeService).getRight();

            // Then
            Assertions.assertThat(run.getRunName()).isEqualTo(EXAMPLE_RUN_NAME);
        }

        @Test
        void should_display_event_specific_information_when_run_name_is_a_whitespace() {
            // Given
            Mockito.when(workflowData.workflowDefinition().getRunName()).thenReturn(new Expression(WHITESPACE));
            Mockito.when(eventData.runName()).thenReturn(EXAMPLE_RUN_NAME);

            // When
            var run = WorkflowRun.crate(workflowData, eventData, EMPTY_INPUTS, timeService).getRight();

            // Then
            Assertions.assertThat(run.getRunName()).isEqualTo(EXAMPLE_RUN_NAME);

        }

        @Test
        void should_display_simple_run_name_when_run_name_is_provided() {
            // Given
            Mockito.when(workflowData.workflowDefinition().getRunName()).thenReturn(new Expression(EXAMPLE_RUN_NAME));

            // When
            var run = WorkflowRun.crate(workflowData, eventData, EMPTY_INPUTS, timeService).getRight();

            // Then
            Assertions.assertThat(run.getRunName()).isEqualTo(EXAMPLE_RUN_NAME);
        }

        @ParameterizedTest
        @MethodSource("complexRunName")
        void should_display_complex_run_name_when_run_name_is_provided(Map<String, DynamicProperty> inputs, Expression expression, String expectedResult) {
            // Given
            Mockito.when(workflowData.workflowDefinition().getRunName()).thenReturn(expression);

            // When
            var run = WorkflowRun.crate(workflowData, eventData, inputs, timeService).getRight();

            // Then
            Assertions.assertThat(run.getRunName()).isEqualTo(expectedResult);
        }

        public static Stream<Arguments> complexRunName() {
            return Stream.<Arguments>builder()
                    .add(inputTestCase("test"))
                    .add(inputTestCase(""))
                    .add(contextTestCase(NO_OP, "${{system.non_existent}}", ""))
                    .add(contextTestCase(() -> {
                        //Mockito.when(context.actor()).thenReturn("Example user");
                    }, "${{system.actor}}", "Example user"))
                    .add(contextTestCase(() -> {
                        //Mockito.when(context.actorId()).thenReturn("1234567");
                    }, "${{system.actor_id}}", "1234567"))
                    .add(contextTestCase(() -> {
                        //Mockito.when(context.baseRef()).thenReturn("main");
                    }, "${{system.base_ref}}", "main"))
                    .build();
        }

        // TODO: Make it more readable
        private static @NonNull Arguments inputTestCase(String test) {
            return Arguments.of(
                    Map.of(
                            "deploy_target", new TextDynamicProperty(test)
                    ),
                    new Expression("${{inputs.deploy_target}}"),
                    test
            );
        }

        private static @NonNull Arguments contextTestCase(Runnable pre, String expression, String expected) {
            pre.run();
            return Arguments.of(
                    EMPTY_INPUTS,
                    new Expression(expression),
                    expected
            );
        }
    }
}

//// Object organizationId;
//// Object repositoryId;
//// Object revision;
//// /**
////  * type: event, id: xxxxxxxx
////  * type: workflow_call, id: xxxxxx
////  */
//// Object correlationId;
//// Map<String, DynamicProperty> inputs;
//// Map<String, String> organizationVariables;
//// Map<String, String> repositoryVariables;
//// Object workflowDefinition;
//// Object runId;
//// Object startDate;
//// Object triggeringEntity;
//// Object event;
//
//var workflowDefinition = WorkflowDefinition.builder()
//        .build();
