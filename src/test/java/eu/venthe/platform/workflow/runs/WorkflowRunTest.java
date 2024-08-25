package eu.venthe.platform.workflow.runs;

import eu.venthe.platform.organization.domain.OrganizationName;
import eu.venthe.platform.repository.domain.RepositoryName;
import eu.venthe.platform.shared_kernel.dynamic_variable.TextDynamicProperty;
import eu.venthe.platform.shared_kernel.git.RevisionHash;
import eu.venthe.platform.shared_kernel.system_events.model.EventType;
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

import java.net.URI;
import java.nio.file.Path;
import java.time.*;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;

class WorkflowRunTest {
    private static final String WHITESPACE = " ";
    private static final OrganizationName EXAMPLE_ORGANIZATION_NAME = new OrganizationName("Example-Organization");
    private static final RepositoryName EXAMPLE_REPOSITORY_NAME = new RepositoryName("Example-Repository");
    private static final OffsetDateTime EXAMPLE_DATE = OffsetDateTime.of(LocalDate.of(2024, Month.APRIL, 1), LocalTime.of(13, 13), ZoneOffset.ofHours(1));
    private static final String EXAMPLE_RUN_NAME = "Example run name";

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    TimeService timeService;

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    WorkflowData workflowData;

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    EventData eventData;

    @Test
    void should_set_the_creation_date_when_created() {
        // Given
        when(eventData.event().getTimestamp()).thenReturn(EXAMPLE_DATE);

        // When
        var run = WorkflowRun.crate(workflowData, eventData, timeService).getRight();

        // Then
        Assertions.assertThat(run.getStartDate()).isEqualTo(EXAMPLE_DATE);
    }

    @Test
    void should_provide_event_when_created() {
        // When
        var result = WorkflowRun.crate(workflowData, eventData, timeService);
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
            when(workflowData.workflowDefinition().getName()).thenReturn(null);
            var workflowPath = Path.of(".pipeline/workflows/example.yml");
            when(workflowData.path()).thenReturn(workflowPath);

            // When
            var run = WorkflowRun.crate(workflowData, eventData, timeService).getRight();

            // Then
            Assertions.assertThat(run.getName()).isEqualTo(workflowPath.toString());
        }

        @Test
        void should_provide_path_as_name_when_workflow_name_is_not_provided() {
            // Given
            when(workflowData.workflowDefinition().getName()).thenReturn(WHITESPACE);
            var workflowPath = Path.of(".pipeline/workflows/example.yml");
            when(workflowData.path()).thenReturn(workflowPath);

            // When
            var run = WorkflowRun.crate(workflowData, eventData, timeService).getRight();

            // Then
            Assertions.assertThat(run.getName()).isEqualTo(workflowPath.toString());
        }

        @Test
        void should_provide_name_from_workflow_when_workflow_name_is_provided() {
            // Given
            var workflowName = "Example";
            when(workflowData.workflowDefinition().getName()).thenReturn(workflowName);

            // When
            var run = WorkflowRun.crate(workflowData, eventData, timeService).getRight();

            // Then
            Assertions.assertThat(run.getName()).isEqualTo(workflowName);
        }
    }

    @Nested
    class RunName {

        private static final BiConsumer<EventData, WorkflowData> NO_OP = (wd, ed) -> {
        };

        @Test
        void should_display_event_specific_information_when_run_name_is_omitted() {
            // Given
            when(workflowData.workflowDefinition().getRunName()).thenReturn(null);

            // When
            var run = WorkflowRun.crate(workflowData, eventData, timeService).getRight();

            // Then
            Assertions.assertThat(run.getRunName()).isEqualTo(EXAMPLE_RUN_NAME);
        }

        @Test
        void should_display_event_specific_information_when_run_name_is_a_whitespace() {
            // Given
            when(workflowData.workflowDefinition().getRunName()).thenReturn(new Expression(WHITESPACE));

            // When
            var run = WorkflowRun.crate(workflowData, eventData, timeService).getRight();

            // Then
            Assertions.assertThat(run.getRunName()).isEqualTo(EXAMPLE_RUN_NAME);

        }

        @Test
        void should_display_simple_run_name_when_run_name_is_provided() {
            // Given
            when(workflowData.workflowDefinition().getRunName()).thenReturn(new Expression(EXAMPLE_RUN_NAME));

            // When
            var run = WorkflowRun.crate(workflowData, eventData, timeService).getRight();

            // Then
            Assertions.assertThat(run.getRunName()).isEqualTo(EXAMPLE_RUN_NAME);
        }

        @ParameterizedTest
        @MethodSource({"workflowInputsContext", "workflowSystemContext", "workflowEventContext"})
        void should_display_complex_run_name_when_run_name_is_provided(Expression expression, String expectedResult, EventData<?> eventData, WorkflowData workflowData) {
            // Given
            when(workflowData.workflowDefinition().getRunName()).thenReturn(expression);

            // When
            var run = WorkflowRun.crate(workflowData, eventData, timeService).getRight();

            // Then
            Assertions.assertThat(run.getRunName()).isEqualTo(expectedResult);
        }

        public static Stream<Arguments> workflowInputsContext() {
            return Stream.<Arguments>builder()
                    .add(testCase(NO_OP, "${{inputs.test}}", "value"))
                    .add(testCase(NO_OP, "${{inputs.[\"test\"]}}", "value"))
                    .add(testCase(NO_OP, "${{inputs.non_existent}}", ""))
                    .add(testCase(NO_OP, "${{inputs[\"non_existent\"]}}", ""))
                    .build();
        }

        public static Stream<Arguments> workflowEventContext() {
            return Stream.<Arguments>builder()
                    .add(testCase(NO_OP, "${{system.event.type}}", "workflow_run"))
                    .build();
        }

        public static Stream<Arguments> workflowSystemContext() {
            return Stream.<Arguments>builder()
                    .add(testCase(NO_OP, "${{system.non_existent}}", ""))
                    .add(testCase(NO_OP, "${{system[\"non_existent\"]}}", ""))
                    .add(testCase(NO_OP, "${{system.actor}}", "Example user"))
                    .add(testCase(NO_OP, "${{system[\"actor\"]}}", "Example user"))
                    .add(testCase(NO_OP, "${{system.actor_id}}", "1234567"))
                    .add(testCase(NO_OP, "${{system.event_name}}", "workflow_run"))

                    .add(testCase(NO_OP, "${{system.head_ref}}", "main"))
                    .add(testCase(NO_OP, "${{system.base_ref}}", "main"))
                    .add(testCase(NO_OP, "${{system.ref}}", "main"))
                    .add(testCase(NO_OP, "${{system.ref_name}}", "<pr_number>/merge"))
                    .add(testCase(NO_OP, "${{system.ref_protected}}", "true"))
                    .add(testCase(NO_OP, "${{system.ref_type}}", "branch"))


                    .add(testCase(NO_OP, "${{system.workflow}}", "Example name"))
                    .add(testCase((eventData, workflowData) -> {
                        when(workflowData.revision().hash()).thenReturn(new RevisionHash("12345"));
                    }, "${{system.workflow_sha}}", "012f0857f22e0b46a7524f07eabac03fed22165a6130fa62bc58fb363667a306"))
                    .add(testCase((eventData, workflowData) -> {
                        when(workflowData.revision().name().fullName()).thenReturn("refs/heads/my_branch");
                        when(workflowData.path()).thenReturn(Path.of(".pipeline/workflows/my-workflow.yml"));
                        when(workflowData.organizationName()).thenReturn(new OrganizationName(EXAMPLE_ORGANIZATION_NAME.value()));
                        when(workflowData.repositoryName()).thenReturn(new RepositoryName(EXAMPLE_REPOSITORY_NAME.value()));
                    }, "${{system.workflow_ref}}", "%s/%s/.pipeline/workflows/my-workflow.yml@refs/heads/my_branch".formatted(EXAMPLE_ORGANIZATION_NAME.value(), EXAMPLE_REPOSITORY_NAME.value())))

                    .add(testCase(NO_OP, "${{system.repository}}", "%s/%s".formatted(EXAMPLE_ORGANIZATION_NAME.value(), EXAMPLE_REPOSITORY_NAME.value())))
                    .add(testCase(NO_OP, "${{system.repository_id}}", "12345"))
                    .add(testCase(NO_OP, "${{system.repository_owner}}", "Example"))
                    .add(testCase(NO_OP, "${{system.repository_owner_id}}", "12345"))
                    .add(testCase(NO_OP, "${{system.repository_url}}", "git://source/Example-Organization/hello-world.git"))
                    .add(testCase(NO_OP, "${{system.retention_days}}", "15"))
                    .add(testCase(NO_OP, "${{system.run_id}}", "49869fac6e8c45a39f35e702783483ce"))
                    .add(testCase(NO_OP, "${{system.run_number}}", "223"))
                    .add(testCase(NO_OP, "${{system.run_attempt}}", "2"))
                    .add(testCase(NO_OP, "${{system.sha}}", "ba7816bf8f01cfea414140de5dae2223b00361a396177a9cb410ff61f20015ad"))
                    .add(testCase(NO_OP, "${{system.triggering_actor}}", "Example triggering actor"))
                    .build();
        }

        // retention_days
        // run_id
        // run_number
        // run_attempt
        // sha
        // triggering_actor

        @NonNull
        private static Arguments testCase(BiConsumer<EventData, WorkflowData> pre, String expression, String expected) {
            var eventData = mock(EventData.class, RETURNS_DEEP_STUBS);
            var workflowData = mock(WorkflowData.class, RETURNS_DEEP_STUBS);
            pre.accept(eventData, workflowData);
            return Arguments.of(
                    new Expression(expression),
                    expected,
                    eventData,
                    workflowData
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
