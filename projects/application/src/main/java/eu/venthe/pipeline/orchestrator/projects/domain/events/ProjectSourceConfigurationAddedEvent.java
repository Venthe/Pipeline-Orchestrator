package eu.venthe.pipeline.orchestrator.projects.domain.events;

import eu.venthe.pipeline.orchestrator.shared_kernel.DomainEvent;
import lombok.Value;
import lombok.experimental.SuperBuilder;

@Value
@SuperBuilder
public class ProjectSourceConfigurationAddedEvent implements DomainEvent {
    String sourceId;
    String sourceType;
}
