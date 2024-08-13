package eu.venthe.platform.organization.domain;

import eu.venthe.platform.organization.domain.source_configuration.plugins.template.ProjectSourcePluginInstance;
import eu.venthe.platform.organization.domain.source_configuration.plugins.template.model.ProjectDto;
import eu.venthe.platform.organization.domain.source_configuration.plugins.template.model.ProjectId;
import eu.venthe.platform.organization.domain.source_configuration.plugins.template.model.Revision;
import eu.venthe.platform.shared_kernel.events.DomainTrigger;
import eu.venthe.platform.shared_kernel.io.File;
import eu.venthe.platform.shared_kernel.io.Metadata;
import lombok.Value;

import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Value
public class Organization {
    OrganizationId organizationId;
    Sources sources;
    ProjectsSynchronizer projectsSynchronizer;

    public List<DomainTrigger> synchronize() {
        return projectsSynchronizer.synchronize();
    }

    public Stream<ProjectDto> listAllProjects() {
        return getDefaultSource().getProjects();
    }

    public Stream<ProjectId> listAllProjectIDs() {
        return getDefaultSource().getProjectIds();
    }

    public Optional<ProjectDto> getProject(ProjectId projectId) {
        return getDefaultSource().getProject(projectId);
    }

    public Optional<File> getFile(ProjectId projectId, Revision revision, Path path) {
        return getDefaultSource().getFile(projectId, revision, path);
    }

    public Collection<Metadata> getFileList(ProjectId projectId, Revision revision, Path path) {
        return getDefaultSource().getFileList(projectId, revision, path);
    }

    private ProjectSourcePluginInstance getDefaultSource() {
        return sources.getByAlias(Sources.SourceAlias.DEFAULT);
    }
}
