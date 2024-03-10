package eu.venthe.pipeline.orchestrator.project_configuration_sources.core.domain.events;

import eu.venthe.pipeline.orchestrator.shared_kernel.DomainEvent;
import lombok.Value;

@Value
public class ProjectSourceConfigurationAddedEvent implements DomainEvent {
    String projectSourceId;
}
