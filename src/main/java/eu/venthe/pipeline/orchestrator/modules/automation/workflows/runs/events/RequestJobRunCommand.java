package eu.venthe.pipeline.orchestrator.modules.automation.workflows.runs.events;

import eu.venthe.pipeline.orchestrator.modules.automation.runners.runner_engine.template.model.RunCallbackToken;
import eu.venthe.pipeline.orchestrator.modules.automation.runners.runner_engine.template.model.dimensions.RunnerDimensions;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.model.JobRunId;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.runs.WorkflowRunId;
import eu.venthe.pipeline.orchestrator.projects.domain.ProjectId;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.DomainTrigger;

public record RequestJobRunCommand(ProjectId projectId,
                                   WorkflowRunId workflowRunId,
                                   JobRunId runId,
                                   RunCallbackToken runCallbackToken,
                                   RunnerDimensions dimensions) implements DomainTrigger {
    @Override
    public String getType() {
        return "request_job";
    }
}
