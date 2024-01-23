package eu.venthe.pipeline.orchestrator.workflow_executions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import eu.venthe.pipeline.orchestrator.events.HandledEvent;
import eu.venthe.pipeline.orchestrator.events.model.EventType;
import eu.venthe.pipeline.orchestrator.workflow_executions.WorkflowExecution.NewJobsDto;
import eu.venthe.pipeline.orchestrator.workflows.Workflow;
import lombok.SneakyThrows;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.function.Consumer;

import static eu.venthe.pipeline.orchestrator.events.model.EventType.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;

class WorkflowExecutionTest {
    private static final ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
    public static final Workflow.WorkflowRef REF = new Workflow.WorkflowRef("Example", "refs/heads/main", ".pipeline/workflows/test.yml", "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855");

    @SneakyThrows
    @Test
    void name() {
        Workflow workflow = getWorkflow("""
                on: workflow_dispatch
                jobs:
                  job1:
                    steps:
                      - run: It works!
                """);
        HandledEvent event = getEvent(e ->
                when(e.getType()).thenReturn(WORKFLOW_DISPATCH)
        );
        WorkflowExecution workflowExecution = WorkflowExecution.from(workflow, event).orElseThrow();

        NewJobsDto jobs = workflowExecution.getNewJobs();

        assertThat(jobs)
                .isNotNull()
                .satisfies(j -> assertThat(j.getJobs())
                        .satisfiesExactlyInAnyOrder(d -> assertThat(d.getId())
                                .isEqualTo("job1")
                        )
                );
    }

    private static Workflow getWorkflow(String data) throws JsonProcessingException {
        return new Workflow((ObjectNode) objectMapper.readTree(data), REF);
    }

    private HandledEvent getEvent(Consumer<HandledEvent> mutator) {
        HandledEvent mock = mock(HandledEvent.class);
        mutator.accept(mock);
        return mock;
    }
}