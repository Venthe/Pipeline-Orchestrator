package eu.venthe.pipeline.workflows.runs.events;

import eu.venthe.pipeline.workflows.runs.WorkflowRunId;
import eu.venthe.pipeline.projects.domain.ProjectId;
import eu.venthe.pipeline.shared_kernel.events.DomainTrigger;

public record WorkflowRunCreatedEvent(ProjectId projectId, WorkflowRunId workflowRunId) implements DomainTrigger {
    @Override
    public String getType() {
        return "workflow_run_created";
    }
}
