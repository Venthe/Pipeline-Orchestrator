package eu.venthe.pipeline.workflow.runs.events;

import eu.venthe.pipeline.workflow.model.JobRunId;
import eu.venthe.pipeline.workflow.runs.WorkflowRunId;
import eu.venthe.pipeline.project.domain.ProjectId;
import eu.venthe.pipeline.shared_kernel.events.DomainTrigger;

public record RequestJobRunCommand(ProjectId projectId,
                                   WorkflowRunId workflowRunId,
                                   JobRunId runId) implements DomainTrigger {
    @Override
    public String getType() {
        return "request_job";
    }
}
