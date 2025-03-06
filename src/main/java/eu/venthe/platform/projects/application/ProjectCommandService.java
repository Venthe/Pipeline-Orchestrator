package eu.venthe.platform.projects.application;

import eu.venthe.platform.projects.domain.*;
import eu.venthe.platform.shared_kernel.events.DomainMessagesBroker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Clock;

@RequiredArgsConstructor
@Service
@Slf4j
public class ProjectCommandService {
    private final ProjectRepository projectRepository;
    private final SourceConfigurationRepository sourceConfigurationRepository;
    private final DomainMessagesBroker messageBroker;
    private final Clock clock;

    public void registerProject(SourceConfigurationInternalIdentifier sourceIdentifier, ProjectCorrelationId projectCorrelationId) {
        log.info("Registering project {} for {}", projectCorrelationId, sourceIdentifier);
        var existingRepository = projectRepository.find(sourceIdentifier, projectCorrelationId);

        if (existingRepository.isPresent()) {
            log.warn("Project already exists");
            return;
        }

        var project = Project.create(
                sourceConfigurationRepository,
                clock,
                sourceIdentifier,
                projectCorrelationId
        );
        projectRepository.save(project.data());
        messageBroker.exchange(project.messages());
        log.debug("Project {} registered for {}", projectCorrelationId, sourceIdentifier);
    }
}
