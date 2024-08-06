package eu.venthe.pipeline.orchestrator.modules.automation.workflows.runs.dependencies;

import eu.venthe.pipeline.orchestrator.modules.automation.runners.runner_engine.template.model.ExecutionCallbackToken;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.model.JobExecutionId;

public interface Runner {
    RunId run(JobExecutionId executionId,
              ExecutionCallbackToken callbackToken);

//    void cancel(RunId id);
}
