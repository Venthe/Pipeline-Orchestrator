package eu.venthe.pipeline.orchestrator.projects.events;

import eu.venthe.pipeline.orchestrator.shared_kernel.events.DomainEvent;
import lombok.Value;

@Value
public class ProjectUpdatedEvent implements DomainEvent {
    String type = "project_updated";
}
