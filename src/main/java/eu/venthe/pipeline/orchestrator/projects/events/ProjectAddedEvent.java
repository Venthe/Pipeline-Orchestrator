package eu.venthe.pipeline.orchestrator.projects.events;

import eu.venthe.pipeline.orchestrator.projects.domain.projects.model.ProjectId;
import eu.venthe.pipeline.orchestrator.projects.domain.projects.model.ProjectStatus;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.DomainEvent;
import lombok.Value;

import java.util.Optional;

@Value
public class ProjectAddedEvent implements DomainEvent {
    String type = "project_added";
    ProjectId id;
    ProjectStatus status;
}
