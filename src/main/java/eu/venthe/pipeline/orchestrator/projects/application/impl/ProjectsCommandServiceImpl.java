package eu.venthe.pipeline.orchestrator.projects.application.impl;

import eu.venthe.pipeline.orchestrator.modules.ProjectModuleMediator;
import eu.venthe.pipeline.orchestrator.organizations.application.dto.CreateProjectSpecificationDto;
import eu.venthe.pipeline.orchestrator.projects.application.ProjectsCommandService;
import eu.venthe.pipeline.orchestrator.projects.domain.Project;
import eu.venthe.pipeline.orchestrator.projects.domain.ProjectId;
import eu.venthe.pipeline.orchestrator.projects.domain.infrastructure.ProjectRepository;
import eu.venthe.pipeline.orchestrator.projects.domain.infrastructure.SourceConfigurationRepository;
import eu.venthe.pipeline.orchestrator.projects.domain.source_configurations.SourceConfigurationId;
import eu.venthe.pipeline.orchestrator.shared_kernel.git.Revision;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProjectsCommandServiceImpl implements ProjectsCommandService {
    private final ProjectRepository repository;
    private final SourceConfigurationRepository configurationRepository;
    private final ProjectModuleMediator projectModuleMediator;

    @Override
    public void registerTrackedRevision(final ProjectId projectId, final Revision revision) {
        log.debug("Registering revision {} for tracking on project {}", revision, projectId);
        var project = repository.find(projectId).orElseThrow();
        project.registerTrackedRevision(revision);
        log.info("Revision {} on project {} registered for tracking", revision, projectId);
    }

    @Override
    public void add(SourceConfigurationId configurationId, CreateProjectSpecificationDto newProjectDto) {
        if (repository.exists(newProjectDto.projectId())) {
            throw new IllegalArgumentException();
        }

        var configuration = configurationRepository.find(configurationId).orElseThrow();

        Project project = new Project(newProjectDto.projectId(), configuration, projectModuleMediator, newProjectDto.description(), newProjectDto.status());

        repository.save(project);
    }
}
