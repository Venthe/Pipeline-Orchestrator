package eu.venthe.platform.project.application.impl;

import eu.venthe.platform.organization.domain.OrganizationName;
import eu.venthe.platform.project.application.ProjectsQueryService;
import eu.venthe.platform.project.application.model.ProjectDto;
import eu.venthe.platform.project.domain.Project;
import eu.venthe.platform.project.domain.ProjectId;
import eu.venthe.platform.project.domain.infrastructure.ProjectRepository;
import eu.venthe.platform.shared_kernel.git.SimpleRevision;
import eu.venthe.platform.shared_kernel.io.File;
import eu.venthe.platform.shared_kernel.io.Metadata;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.togglz.core.manager.FeatureManager;
import org.togglz.core.util.NamedFeature;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.time.Instant;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class ProjectsQueryServiceImpl implements ProjectsQueryService {
    private final ProjectRepository projectRepository;
    private final FeatureManager featureManager;

    @Override
    public Collection<ProjectDto> listProjects() {
        return projectRepository.findAll().stream().map(ProjectsQueryServiceImpl::toProjectDto).collect(Collectors.toSet());
    }

    @Override
    public Optional<ProjectDto> find(ProjectId projectId) {
        return projectRepository.find(projectId).map(ProjectsQueryServiceImpl::toProjectDto);
    }

    @Override
    public Stream<ProjectId> getProjectIds(OrganizationName organizationName) {
        return projectRepository.findAll().stream().map(Project::getId).filter(id -> id.getOrganizationName().equals(organizationName));
    }

    @Override
    public Optional<File> getFile(final ProjectId id, final SimpleRevision revision, final Path file) {
        if (!featureManager.isActive(new NamedFeature("GENERAL_WIP"))) {
            throw new UnsupportedOperationException();
        }

        return projectRepository.find(id).orElseThrow().getFile(revision, file);
    }

    private static ProjectDto toProjectDto(Project p) {
        return new ProjectDto(p.getId(), p.getStatus());
    }
}
