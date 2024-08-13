package eu.venthe.pipeline.workflow.runs.events;

import eu.venthe.pipeline.workflow.runs.WorkflowRunId;
import eu.venthe.pipeline.project.domain.ProjectId;
import eu.venthe.pipeline.shared_kernel.events.DomainTrigger;

public record WorkflowRunCreatedEvent(ProjectId projectId, WorkflowRunId workflowRunId) implements DomainTrigger {
    @Override
    public String getType() {
        return "workflow_run_created";
    }
}
