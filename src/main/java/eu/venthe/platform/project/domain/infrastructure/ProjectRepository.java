package eu.venthe.platform.project.domain.infrastructure;

import eu.venthe.platform.project.domain.Project;
import eu.venthe.platform.project.domain.ProjectId;
import eu.venthe.platform.shared_kernel.DomainRepository;

public interface ProjectRepository extends DomainRepository<Project, ProjectId> {
}
