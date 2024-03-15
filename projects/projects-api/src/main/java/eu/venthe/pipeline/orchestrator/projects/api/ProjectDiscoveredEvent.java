package eu.venthe.pipeline.orchestrator.projects.api;

import eu.venthe.pipeline.orchestrator.shared_kernel.DomainEvent;
import lombok.Value;

@Value
public class ProjectDiscoveredEvent implements DomainEvent {
    String projectName;
    String systemId;
}
