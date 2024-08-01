package eu.venthe.pipeline.orchestrator.modules.automation.workflows.runs;

import eu.venthe.pipeline.orchestrator.modules.automation.workflows.definition.WorkflowDefinition;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.WorkflowExecutionCommandService;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.definition.contexts.JobsContext;
import eu.venthe.pipeline.orchestrator.utilities.EnvUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WorkflowExecutionJobs {
    private final JobsContext jobx;

    public WorkflowExecutionJobs(final JobsContext jobx) {
        this.jobx = jobx;
    }

    void start(final WorkflowExecutionCommandService.Context context, final EnvUtil util) {
        log.info("{}", jobx);
        throw new UnsupportedOperationException();
    }
}
