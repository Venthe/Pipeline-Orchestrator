package eu.venthe.pipeline.workflows.runs.events;

import eu.venthe.pipeline.workflows.model.JobRunId;
import eu.venthe.pipeline.workflows.runs.WorkflowRunId;
import eu.venthe.pipeline.projects.domain.ProjectId;
import eu.venthe.pipeline.shared_kernel.events.DomainTrigger;

public record RequestJobRunCommand(ProjectId projectId,
                                   WorkflowRunId workflowRunId,
                                   JobRunId runId) implements DomainTrigger {
    @Override
    public String getType() {
        return "request_job";
    }
}
