package eu.venthe.pipeline.orchestrator.workflow_executions.domain;

import eu.venthe.pipeline.orchestrator.modules.workflow.domain.WorkflowExecution;
import eu.venthe.pipeline.orchestrator.modules.workflow.domain.WorkflowTemplate;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;

class WorkflowExecutionTest {
    @Test
    void name() {
        var mock = mock(WorkflowTemplate.class);
        var execution = new WorkflowExecution(mock);

        execution.triggerWorkflow();
    }
}
