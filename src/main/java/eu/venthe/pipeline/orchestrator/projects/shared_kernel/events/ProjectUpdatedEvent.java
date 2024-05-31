package eu.venthe.pipeline.orchestrator.projects.shared_kernel.events;

import eu.venthe.pipeline.orchestrator.projects.domain.projects.ProjectId;
import eu.venthe.pipeline.orchestrator.projects.domain.projects.model.ProjectStatus;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.DomainTrigger;
import lombok.Value;

import java.util.Optional;

@Value
public class ProjectUpdatedEvent implements DomainTrigger {
    String type = "project_updated";
    ProjectId id;
    ProjectStatus status;
    Optional<String> description;
}
