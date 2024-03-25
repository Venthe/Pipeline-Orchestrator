package eu.venthe.pipeline.orchestrator.projects_source.api.events;

import eu.venthe.pipeline.orchestrator.shared_kernel.DomainEvent;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.SuperBuilder;

@Value
public class ProjectSourceConfigurationAddedEvent implements DomainEvent {
    String sourceId;
    String sourceType;
    String type = "project_source_configuration_added";
}
