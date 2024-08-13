package eu.venthe.platform.organization.domain;

import eu.venthe.platform.organization.domain.source_configuration.plugins.template.ProjectSourcePluginInstance;
import eu.venthe.platform.organization.domain.source_configuration.plugins.template.model.ProjectDto;
import eu.venthe.platform.organization.domain.source_configuration.plugins.template.model.ProjectId;
import eu.venthe.platform.organization.domain.source_configuration.plugins.template.model.Revision;
import eu.venthe.platform.project.application.ProjectsQueryService;
import eu.venthe.platform.shared_kernel.events.DomainTrigger;
import eu.venthe.platform.shared_kernel.io.File;
import eu.venthe.platform.shared_kernel.io.Metadata;
import lombok.Value;

import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

@Value
public class Organization {
    OrganizationId organizationId;
    Map<SourceId, ProjectSourcePluginInstance> sources;
    ProjectsSynchronizer projectsSynchronizer;
    ProjectsQueryService projectsQueryService;

    public List<DomainTrigger> synchronize() {
        projectsSynchronizer.synchronize(new ProjectsSynchronizer.ProjectsProvider() {
            @Override
            public Stream<ProjectId> listProjectsFromSource() {
                return getDefaultSource().getProjectIds();
            }

            @Override
            public Stream<ProjectId> listRegisteredProjects() {
                return projectsQueryService.getProjectIds(organizationId).map(e->e.getName());
            }
        });
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
        return sources.get(SourceId.DEFAULT);
    }
}
