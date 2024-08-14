package eu.venthe.platform.workflow.runs.events;

import eu.venthe.platform.workflow.model.JobRunId;
import eu.venthe.platform.workflow.runs.WorkflowRunId;
import eu.venthe.platform.project.domain.ProjectId;
import eu.venthe.platform.shared_kernel.events.DomainTrigger;

public record RequestJobRunCommand(ProjectId projectId,
                                   WorkflowRunId workflowRunId,
                                   JobRunId runId) implements DomainTrigger {
    @Override
    public String getType() {
        return "request_job";
    }
}
