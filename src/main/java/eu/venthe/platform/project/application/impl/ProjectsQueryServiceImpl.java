package eu.venthe.platform.project.application.impl;

import eu.venthe.platform.namespace.domain.NamespaceName;
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
    public Stream<ProjectId> getProjectIds(NamespaceName namespaceName) {
        return projectRepository.findAll().stream().map(Project::getId).filter(id -> id.getNamespaceName().equals(namespaceName));
    }

    @Override
    public Optional<File> getFile(final ProjectId id, final SimpleRevision revision, final Path file) {
        if (!featureManager.isActive(new NamedFeature("GENERAL_WIP"))) {
            throw new UnsupportedOperationException();
        }

        var data = """
                name: learn-github-actions
                on: workflow_dispatch
                jobs:
                  check-bats-version:
                    name: My first job
                    steps:
                      - name: Run echo
                        run: echo "Hello world!"
                """;

        return Optional.of(new File(new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8)), new Metadata("whatever", Metadata.FileType.FILE, 12, Instant.MAX, Instant.MAX, true, true, true)));
    }

    private static ProjectDto toProjectDto(Project p) {
        return new ProjectDto(p.getId(), p.getStatus());
    }
}
