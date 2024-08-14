package eu.venthe.platform.project.application.impl;

import eu.venthe.platform.project.application.ProjectsCommandService;
import eu.venthe.platform.project.application.model.CreateProjectSpecification;
import eu.venthe.platform.project.domain.Project;
import eu.venthe.platform.project.domain.infrastructure.ProjectRepository;
import eu.venthe.platform.shared_kernel.events.Envelope;
import eu.venthe.platform.shared_kernel.events.MessageBroker;
import eu.venthe.platform.source_configuration.application.SourceQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProjectsCommandServiceImpl implements ProjectsCommandService {
    private final ProjectRepository repository;
    private final SourceQueryService sourceQueryService;
    private final MessageBroker messageBroker;

    @Override
    public void add(CreateProjectSpecification createProjectSpecification) {
        if (repository.exists(createProjectSpecification.projectId())) {
            throw new IllegalArgumentException();
        }

        log.trace("Saving project {}", createProjectSpecification);
        var result = Project.create(createProjectSpecification.projectId(), createProjectSpecification.configurationSourceId(), sourceQueryService);

        repository.save(result.getKey());
        messageBroker.exchange(Envelope.from(result.getValue()));
        log.debug("Project saved {}", result.getKey());
    }
}
