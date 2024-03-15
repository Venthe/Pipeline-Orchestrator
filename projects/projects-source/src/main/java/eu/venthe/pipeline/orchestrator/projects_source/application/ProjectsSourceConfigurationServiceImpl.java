package eu.venthe.pipeline.orchestrator.projects_source.application;

import eu.venthe.pipeline.orchestrator.plugins.projects.ReadProjectSourceConfigurationDto;
import eu.venthe.pipeline.orchestrator.projects.projects.application.ProjectsSourceRepository;
import eu.venthe.pipeline.orchestrator.projects_source.domain.ProjectSourceConfiguration;
import eu.venthe.pipeline.orchestrator.projects_source.domain.ProjectSourceConfigurationFactory;
import eu.venthe.pipeline.orchestrator.projects_source.domain.ProjectSourceConfigurationId;
import eu.venthe.pipeline.orchestrator.shared_kernel.DomainEvent;
import eu.venthe.pipeline.orchestrator.shared_kernel.DomainMessageBroker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProjectsSourceConfigurationServiceImpl implements ProjectsSourceConfigurationService {
    private final ProjectsSourceRepository repository;
    private final DomainMessageBroker bus;
    private final ProjectSourceConfigurationFactory factory;

    @Override
    public ProjectSourceConfigurationId addProjectSourceConfiguration(String id, String sourceType, Map<String, String> properties) {
        Pair<ProjectSourceConfiguration, Collection<DomainEvent>> result = factory.createConfiguration(id, sourceType, properties);

        ProjectSourceConfiguration configuration = result.getLeft();
        repository.save(configuration);
        bus.publish(result.getRight());

        log.info("Project source configuration added. {}, {}, {}", id, sourceType, properties);
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
    public Set<ReadProjectSourceConfigurationDto> listConfigurations() {
        return repository.findAll().stream()
                .map(projectSourceConfiguration -> projectSourceConfiguration.visitor(ReadProjectSourceConfigurationDto::new))
                .collect(Collectors.toSet());
    }

    @Override
    public Optional<ReadProjectSourceConfigurationDto> getConfiguration(ProjectSourceConfigurationId projectSourceConfigurationId) {
        return repository.find(projectSourceConfigurationId)
                .map(projectSourceConfiguration -> projectSourceConfiguration.visitor(ReadProjectSourceConfigurationDto::new));
    }

}
