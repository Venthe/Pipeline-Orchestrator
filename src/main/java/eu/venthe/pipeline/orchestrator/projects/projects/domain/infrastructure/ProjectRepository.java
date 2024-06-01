package eu.venthe.pipeline.orchestrator.projects.projects.domain.infrastructure;

import eu.venthe.pipeline.orchestrator.projects.projects.domain.Project;
import eu.venthe.pipeline.orchestrator.projects.projects.domain.ProjectId;
import eu.venthe.pipeline.orchestrator.shared_kernel.DomainRepository;

public interface ProjectRepository extends DomainRepository<Project, ProjectId> {
}
