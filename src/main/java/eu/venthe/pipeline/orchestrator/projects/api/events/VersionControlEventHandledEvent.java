package eu.venthe.pipeline.orchestrator.projects.api.events;

import eu.venthe.pipeline.orchestrator.shared_kernel.events.DomainEvent;
import lombok.Value;

@Value
public class VersionControlEventHandledEvent implements DomainEvent {
    String type = "version_control_event_handled";
}
