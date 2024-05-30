/*
package eu.venthe.pipeline.orchestrator.projects.domain.projects;

import eu.venthe.pipeline.orchestrator.projects.domain.Project;
import eu.venthe.pipeline.orchestrator.projects.domain.ProjectsSourceConfiguration;
import eu.venthe.pipeline.orchestrator.projects.domain.ProjectId;
import eu.venthe.pipeline.orchestrator.projects.domain.projects.model.ProjectStatus;
import eu.venthe.pipeline.orchestrator.projects.shared_kernel.events.ProjectAddedEvent;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.DomainTrigger;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.Optional;

public class ProjectFactory {
    public Pair<Project, List<DomainTrigger>> create(ProjectId p, ProjectStatus status, ProjectsSourceConfiguration projectsSourceConfiguration) {
        return Pair.of(
                new Project(p, Optional.empty(), status, projectsSourceConfiguration),
                List.of(new ProjectAddedEvent(p, status))
        );
    }
}
*/