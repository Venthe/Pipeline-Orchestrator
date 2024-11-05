package eu.venthe.platform.projects.application;

import eu.venthe.platform.projects.domain.Project;
import eu.venthe.platform.projects.domain.ProjectRepository;
import eu.venthe.platform.projects.domain.SourceConfigurationRepository;
import eu.venthe.platform.shared_kernel.ClockService;
import eu.venthe.platform.shared_kernel.events.DomainMessagesBroker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class ProjectCommandService {
    private final ProjectRepository projectRepository;
    private final SourceConfigurationRepository sourceConfigurationRepository;
    private final DomainMessagesBroker messageBroker;
    private final ClockService clockService;

    public void registerProject(String sourceName, String projectName) {
        log.info("Registering project {} for {}", projectName, sourceName);
        var existingRepository = projectRepository.find(sourceName, projectName);

        if (existingRepository.isPresent()) {
            log.warn("Project already existing");
            return;
        }

        var project = Project.create(sourceConfigurationRepository, clockService, sourceName, projectName);
        projectRepository.save(project.data());
        messageBroker.exchange(project.messages());
        log.debug("Project {} registered for {}", projectName, sourceName);
    }
}
