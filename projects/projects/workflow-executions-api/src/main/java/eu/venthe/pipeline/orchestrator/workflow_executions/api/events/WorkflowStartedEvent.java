package eu.venthe.pipeline.orchestrator.workflow_executions.api.events;

import eu.venthe.pipeline.orchestrator.shared_kernel.events.DomainEvent;
import lombok.Value;

@Value
public class WorkflowStartedEvent implements DomainEvent {
    String type = "workflow_finished_event";
}
