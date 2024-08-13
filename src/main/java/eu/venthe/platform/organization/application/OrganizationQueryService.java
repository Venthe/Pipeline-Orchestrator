package eu.venthe.platform.organization.application;

import eu.venthe.platform.organization.domain.OrganizationId;
import eu.venthe.platform.organization.domain.source_configuration.plugins.template.model.ProjectDto;
import eu.venthe.platform.organization.domain.source_configuration.plugins.template.model.ProjectId;
import eu.venthe.platform.organization.domain.source_configuration.plugins.template.model.Revision;
import eu.venthe.platform.shared_kernel.io.File;
import eu.venthe.platform.shared_kernel.io.Metadata;

import java.nio.file.Path;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;

public interface OrganizationQueryService {
    Stream<ProjectDto> listAllProjects(OrganizationId organizationId);

    Stream<ProjectId> listAllProjectIDs(OrganizationId organizationId);

    Optional<ProjectDto> getProject(OrganizationId organizationId, ProjectId projectId);

    Optional<File> getFile(OrganizationId organizationId, ProjectId projectId, Revision revision, Path path);

    Collection<Metadata> getFileList(OrganizationId organizationId, ProjectId projectId, Revision revision, Path path);
}
