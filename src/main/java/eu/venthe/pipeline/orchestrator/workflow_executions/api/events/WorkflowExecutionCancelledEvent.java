package eu.venthe.pipeline.orchestrator.workflow_executions.api.events;

import eu.venthe.pipeline.orchestrator.shared_kernel.events.DomainEvent;
import lombok.Value;

@Value
public class WorkflowExecutionCancelledEvent implements DomainEvent {
    String type = "workflow_execution_cancelled_event";
}
