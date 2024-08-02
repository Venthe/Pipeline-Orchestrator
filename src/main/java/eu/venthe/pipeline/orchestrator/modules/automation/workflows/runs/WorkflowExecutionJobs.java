package eu.venthe.pipeline.orchestrator.modules.automation.workflows.runs;

import eu.venthe.pipeline.orchestrator.modules.automation.workflows.WorkflowExecutionCommandService;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.definition.contexts.JobsContext;
import eu.venthe.pipeline.orchestrator.utilities.EnvUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class WorkflowExecutionJobs {
    private final JobsContext jobs;

    void start(final WorkflowExecutionCommandService.Context context, final EnvUtil util) {
        log.info("{}", jobs);
        throw new UnsupportedOperationException();
    }
}
