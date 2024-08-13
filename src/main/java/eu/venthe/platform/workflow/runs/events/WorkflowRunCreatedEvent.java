package eu.venthe.platform.workflow.runs.events;

import eu.venthe.platform.workflow.runs.WorkflowRunId;
import eu.venthe.platform.project.domain.ProjectId;
import eu.venthe.platform.shared_kernel.events.DomainTrigger;

public record WorkflowRunCreatedEvent(ProjectId projectId, WorkflowRunId workflowRunId) implements DomainTrigger {
    @Override
    public String getType() {
        return "workflow_run_created";
    }
}
