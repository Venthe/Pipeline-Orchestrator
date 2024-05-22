package eu.venthe.pipeline.orchestrator.projects.domain.infrastructure;

import eu.venthe.pipeline.orchestrator.projects.domain.projects.Project;
import eu.venthe.pipeline.orchestrator.projects.domain.projects.model.ProjectId;
import eu.venthe.pipeline.orchestrator.shared_kernel.DomainRepository;

public interface ProjectRepository extends DomainRepository<Project, ProjectId> {
}
