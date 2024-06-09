package eu.venthe.pipeline.orchestrator.organizations.domain.infrastructure;

import eu.venthe.pipeline.orchestrator.organizations.domain.projects.Project;
import eu.venthe.pipeline.orchestrator.organizations.domain.projects.ProjectId;
import eu.venthe.pipeline.orchestrator.shared_kernel.DomainRepository;

public interface ProjectRepository extends DomainRepository<Project, ProjectId> {
}
