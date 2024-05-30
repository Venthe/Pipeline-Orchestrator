package eu.venthe.pipeline.orchestrator.projects.shared_kernel.events;

import eu.venthe.pipeline.orchestrator.shared_kernel.events.DomainTrigger;
import lombok.Value;

@Value
public class ProjectSourceConfigurationRemovedEvent implements DomainTrigger {
    String sourceId;
    String type = "project_source_configuration_removed";
}
