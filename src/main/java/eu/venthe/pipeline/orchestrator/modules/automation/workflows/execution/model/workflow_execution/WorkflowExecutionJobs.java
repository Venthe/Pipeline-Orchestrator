package eu.venthe.pipeline.orchestrator.modules.automation.workflows.execution.model.workflow_execution;

import eu.venthe.pipeline.orchestrator.modules.automation.workflows.definition.WorkflowDefinition;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.WorkflowExecutionCommandService;
import eu.venthe.pipeline.orchestrator.utilities.EnvUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WorkflowExecutionJobs {
    private final WorkflowDefinition execution;

    public WorkflowExecutionJobs(final WorkflowDefinition execution) {
        this.execution = execution;
    }

    void start(final WorkflowExecutionCommandService.Context context, final EnvUtil util) {
        log.info("{}", execution.getJobs());
        throw new UnsupportedOperationException();
    }
}
