package eu.venthe.pipeline.orchestrator.projects.application;

import eu.venthe.pipeline.orchestrator.plugins.projects.ProjectSourceConfigurationDto;
import eu.venthe.pipeline.orchestrator.projects.domain.ProjectSourceConfiguration;
import eu.venthe.pipeline.orchestrator.projects.domain.ProjectSourceConfigurationFactory;
import eu.venthe.pipeline.orchestrator.projects.domain.ProjectSourceConfigurationId;
import eu.venthe.pipeline.orchestrator.projects.domain.ProjectSourceVisitor;
import eu.venthe.pipeline.orchestrator.shared_kernel.DomainEvent;
import eu.venthe.pipeline.orchestrator.shared_kernel.DomainMessageBroker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProjectsSourceConfigurationServiceImpl implements ProjectsSourceConfigurationService {
    private final ProjectsSourceRepository repository;
    private final DomainMessageBroker bus;
    private final ProjectSourceConfigurationFactory factory;

    @Override
    public ProjectSourceConfigurationId addProjectSourceConfiguration(ProjectSourceConfigurationDto configurationDto) {
        Pair<ProjectSourceConfiguration, Collection<DomainEvent>> result = factory.create(configurationDto);

        ProjectSourceConfiguration configuration = result.getLeft();
        repository.save(configuration);
        bus.publish(result.getRight());

        log.info("Project source configuration added. {}", configurationDto);
        return configuration.getId();
    }

    @Override
    public void synchronizeProjects(ProjectSourceConfigurationId projectSourceConfigurationId) {
        ProjectSourceConfiguration configuration = repository.find(projectSourceConfigurationId)
                .orElseThrow(ProjectSourceConfigurationNotFoundException::new);

        Collection<DomainEvent> result = configuration.synchronize();
        bus.publish(result);
        log.info("Projects synchronized for {}", projectSourceConfigurationId);
    }

    @Override
    public void removeProjectSourceConfiguration(ProjectSourceConfigurationId projectSourceConfigurationId) {
        ProjectSourceConfiguration configuration = repository.find(projectSourceConfigurationId)
                .orElseThrow(ProjectSourceConfigurationNotFoundException::new);

        Collection<DomainEvent> result = configuration.delete();
        repository.delete(projectSourceConfigurationId);
        bus.publish(result);
    }

    @Override
    public Set<ProjectSourceConfigurationDto> listConfigurations() {
        return repository.findAll().stream().map(a -> a.visitor(visitor)).collect(Collectors.toSet());
    }

    @Override
    public Optional<ProjectSourceConfigurationDto> getConfiguration(ProjectSourceConfigurationId projectSourceConfigurationId) {
        throw new UnsupportedOperationException();
    }

    private ProjectSourceVisitor<ProjectSourceConfigurationDto> visitor = (String id, String sourceType, HashSet<String> es) -> {
        //fixme:
        throw new UnsupportedOperationException();
    };
}
