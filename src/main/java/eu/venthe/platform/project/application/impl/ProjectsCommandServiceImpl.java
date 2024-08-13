package eu.venthe.platform.project.application.impl;

import eu.venthe.platform.application.modules.ProjectModuleMediator;
import eu.venthe.platform.project.application.dto.CreateProjectSpecificationDto;
import eu.venthe.platform.project.application.ProjectsCommandService;
import eu.venthe.platform.project.domain.Project;
import eu.venthe.platform.project.domain.ProjectId;
import eu.venthe.platform.project.domain.infrastructure.ProjectRepository;
import eu.venthe.platform.project.domain.infrastructure.SourceConfigurationRepository;
import eu.venthe.platform.project.domain.source_configurations.SourceConfigurationId;
import eu.venthe.platform.shared_kernel.git.GitRevision;
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
    public void registerTrackedRevision(final ProjectId projectId, final GitRevision revision) {
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

        Project project = new Project(newProjectDto.projectId(), configuration, projectModuleMediator);

        repository.save(project);
    }
}
