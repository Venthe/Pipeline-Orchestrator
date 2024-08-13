package eu.venthe.pipeline.project.domain.infrastructure;

import eu.venthe.pipeline.project.domain.Project;
import eu.venthe.pipeline.project.domain.ProjectId;
import eu.venthe.pipeline.shared_kernel.DomainRepository;

public interface ProjectRepository extends DomainRepository<Project, ProjectId> {
}
