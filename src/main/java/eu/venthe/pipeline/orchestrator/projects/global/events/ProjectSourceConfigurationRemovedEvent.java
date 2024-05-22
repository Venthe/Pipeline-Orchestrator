package eu.venthe.pipeline.orchestrator.projects.global.events;

import eu.venthe.pipeline.orchestrator.shared_kernel.events.DomainEvent;
import lombok.Value;

@Value
public class ProjectSourceConfigurationRemovedEvent implements DomainEvent {
    String sourceId;
    String type = "project_source_configuration_removed";
}
