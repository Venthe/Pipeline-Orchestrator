package eu.venthe.pipeline.orchestrator.projects.api.events;

import eu.venthe.pipeline.orchestrator.shared_kernel.events.DomainEvent;
import lombok.Value;

@Value
public class ManualWorkflowUnregisteredEvent implements DomainEvent {
    String type = "manual_workflow_unregistered";
}
