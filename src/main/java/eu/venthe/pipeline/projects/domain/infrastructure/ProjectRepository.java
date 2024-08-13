package eu.venthe.pipeline.projects.domain.infrastructure;

import eu.venthe.pipeline.projects.domain.Project;
import eu.venthe.pipeline.projects.domain.ProjectId;
import eu.venthe.pipeline.shared_kernel.DomainRepository;

public interface ProjectRepository extends DomainRepository<Project, ProjectId> {
}
