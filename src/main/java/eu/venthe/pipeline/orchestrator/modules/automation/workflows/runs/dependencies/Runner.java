package eu.venthe.pipeline.orchestrator.modules.automation.workflows.runs.dependencies;

import eu.venthe.pipeline.orchestrator.modules.automation.runners.runner_engine.template.model.ExecutionCallbackToken;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.model.JobRunId;

public interface Runner {
    RunId run(JobRunId executionId,
              ExecutionCallbackToken callbackToken);

//    void cancel(RunId id);
}
