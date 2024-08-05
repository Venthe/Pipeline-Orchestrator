package eu.venthe.pipeline.orchestrator.modules.automation.workflows.runs.dependencies;

import eu.venthe.pipeline.orchestrator.modules.automation.runners.runner_engine.template.model.ExecutionCallbackToken;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.definition.contexts.JobId;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.runs.WorkflowRunId;
import eu.venthe.pipeline.orchestrator.projects.domain.ProjectId;

public interface Runner {
    RunId run(ProjectId projectId,
              WorkflowRunId workflowRunId,
              JobId jobId,
              int runAttempt,
              ExecutionCallbackToken callbackToken);

    void cancel(RunId id);
}
