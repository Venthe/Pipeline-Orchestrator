package eu.venthe.pipeline.orchestrator.projects_source.domain.events;

import eu.venthe.pipeline.orchestrator.shared_kernel.DomainEvent;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.SuperBuilder;

@Value
public class ProjectSourceConfigurationAddedEvent implements DomainEvent {
    String sourceId;
    String sourceType;
}
