package eu.venthe.pipeline.orchestrator.projects_source.api.events;

import eu.venthe.pipeline.orchestrator.shared_kernel.DomainEvent;
import lombok.Value;

@Value
public class ProjectSourceConfigurationRemovedEvent implements DomainEvent {
    String type = "project_source_configuration_removed";
}
