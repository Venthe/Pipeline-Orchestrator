package eu.venthe.pipeline.orchestrator.organizations.domain.projects.domain.infrastructure;

import eu.venthe.pipeline.orchestrator.organizations.domain.projects.domain.Project;
import eu.venthe.pipeline.orchestrator.organizations.domain.projects.domain.model.ProjectId;
import eu.venthe.pipeline.orchestrator.shared_kernel.DomainRepository;

public interface ProjectRepository extends DomainRepository<Project, ProjectId> {
}
