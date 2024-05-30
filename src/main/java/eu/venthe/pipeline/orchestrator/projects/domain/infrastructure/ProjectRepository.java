package eu.venthe.pipeline.orchestrator.projects.domain.infrastructure;

import eu.venthe.pipeline.orchestrator.projects.domain.Project;
import eu.venthe.pipeline.orchestrator.projects.domain.ProjectId;
import eu.venthe.pipeline.orchestrator.projects.domain.ProjectsSourceConfigurationId;
import eu.venthe.pipeline.orchestrator.shared_kernel.DomainRepository;

import java.util.stream.Stream;

public interface ProjectRepository extends DomainRepository<Project, ProjectId> {
}
