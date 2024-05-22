package eu.venthe.pipeline.orchestrator.projects._projects.api.events;

import eu.venthe.pipeline.orchestrator.shared_kernel.events.DomainEvent;
import lombok.Value;

@Value
public class ManualWorkflowRegisteredEvent implements DomainEvent {
    String type = "manual_workflow_registered";
}
