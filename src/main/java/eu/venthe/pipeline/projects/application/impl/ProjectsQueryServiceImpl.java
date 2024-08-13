package eu.venthe.pipeline.projects.application.impl;

import eu.venthe.pipeline.workflows.utilities.GlobPatternMatching;
import eu.venthe.pipeline.projects.application.ProjectsQueryService;
import eu.venthe.pipeline.projects.application.dto.ProjectDetailsDto;
import eu.venthe.pipeline.projects.application.dto.ProjectDto;
import eu.venthe.pipeline.projects.application.dto.WorkflowTaskDto;
import eu.venthe.pipeline.projects.domain.Project;
import eu.venthe.pipeline.projects.domain.ProjectId;
import eu.venthe.pipeline.projects.domain.infrastructure.ProjectRepository;
import eu.venthe.pipeline.projects.domain.source_configurations.SourceConfigurationId;
import eu.venthe.pipeline.shared_kernel.git.GitRevision;
import eu.venthe.pipeline.shared_kernel.io.File;
import eu.venthe.pipeline.shared_kernel.io.Metadata;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.togglz.core.manager.FeatureManager;
import org.togglz.core.util.NamedFeature;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Path;
import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class ProjectsQueryServiceImpl implements ProjectsQueryService {
    private final ProjectRepository projectRepository;
    private final FeatureManager featureManager;

    @Override
    public Collection<ProjectDto> listProjects() {
        return projectRepository.findAll().stream()
                .map(ProjectsQueryServiceImpl::toProjectDto)
                .collect(Collectors.toSet());
    }

    @Override
    public Optional<ProjectDto> find(ProjectId projectId) {
        return projectRepository.find(projectId).map(ProjectsQueryServiceImpl::toProjectDto);
    }

    @Override
    public List<WorkflowTaskDto> showAllTasks() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Stream<ProjectId> getProjectIds(SourceConfigurationId configurationId) {
        return projectRepository.findAll().stream()
                .filter(e -> e.getOwningConfiguration()
                        .getConfigurationId()
                        .equals(configurationId)
                )
                .map(Project::getId);
    }

    @Override
    public Set<File> getFiles(final GlobPatternMatching.Glob pattern) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<File> getFile(final ProjectId id, final GitRevision revision, final Path file) {
        if (!featureManager.isActive(new NamedFeature("GENERAL_WIP"))) {
            throw new UnsupportedOperationException();
        }

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream(); PrintStream printStream = new PrintStream(outputStream)) {
            printStream.print("""
                    name: learn-github-actions
                    on: workflow_dispatch
                    jobs:
                      check-bats-version:
                        name: My first job
                        steps:
                          - name: Run echo
                            run: echo "Hello world!"
                    """);

            return Optional.of(new File(
                    outputStream,
                    new Metadata(
                            "whatever",
                            12,
                            Instant.MAX,
                            Instant.MAX,
                            true,
                            true,
                            true
                    )
            ));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<ProjectDetailsDto> getProjectDetails(final ProjectId id) {
        throw new UnsupportedOperationException();
    }

    private static ProjectDto toProjectDto(Project p) {
        return new ProjectDto(p.getId().getName(), p.getId().getConfigurationId().id(), p.getStatus());
    }
}
