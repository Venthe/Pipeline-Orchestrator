package eu.venthe.pipeline.orchestrator.projects._archive.api.events;

import eu.venthe.pipeline.orchestrator.projects.domain.model.ProjectStatus;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.DomainEvent;
import lombok.Value;

@Value
public class ProjectDiscoveredEvent implements DomainEvent {
    String projectName;
    String systemId;
    String type = "project_discovered";
    ProjectStatus status;
}
