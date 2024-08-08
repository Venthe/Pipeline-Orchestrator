package eu.venthe.pipeline.orchestrator.modules.automation.workflows.runs.events;

import eu.venthe.pipeline.orchestrator.modules.automation.workflows.runs.WorkflowRunId;
import eu.venthe.pipeline.orchestrator.projects.domain.ProjectId;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.DomainTrigger;

public record WorkflowRunCreatedEvent(ProjectId projectId, WorkflowRunId workflowRunId) implements DomainTrigger {
    @Override
    public String getType() {
        return "workflow_run_created";
    }
}
