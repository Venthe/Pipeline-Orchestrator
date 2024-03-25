package eu.venthe.pipeline.orchestrator.workflow_executions.api.events;

import eu.venthe.pipeline.orchestrator.shared_kernel.DomainEvent;

public class WorkflowCancelledEvent implements DomainEvent {
    @Override
    public String getType() {
        return "workflow_cancelled_event";
    }
}
