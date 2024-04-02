package eu.venthe.pipeline.orchestrator.projects_source.application;

import eu.venthe.pipeline.orchestrator.plugins.projects.ProjectPlugin;
import eu.venthe.pipeline.orchestrator.projects_source.api.ProjectsSourceConfigurationQueryService;
import eu.venthe.pipeline.orchestrator.projects_source.api.ReadProjectSourceConfigurationDto;
import eu.venthe.pipeline.orchestrator.projects_source.api.ProjectsSourceConfigurationCommandService;
import eu.venthe.pipeline.orchestrator.projects_source.domain.*;
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
public class ProjectsSourceConfigurationServiceImpl implements ProjectsSourceConfigurationCommandService, ProjectsSourceConfigurationQueryService {
    private final ProjectsSourceRepository repository;
    private final DomainMessageBroker messageBroker;
    private final ProjectSourceConfigurationFactory factory;
    private final ProjectSourcePluginQueryService pluginQueryService;

    @Override
    public String addProjectSourceConfiguration(String id, String sourceType, Map<String, String> properties) {
        Pair<ProjectSourceConfiguration, Collection<DomainEvent>> result = factory.createConfiguration(id, sourceType, properties);

        ProjectSourceConfiguration configuration = result.getLeft();
        repository.save(configuration);
        messageBroker.publish(result.getRight());

        log.info("Project source configuration added. {}, {}, {}", id, sourceType, properties);
        return configuration.getId().getValue();
    }

    @Override
    public void synchronizeProjects(String projectSourceConfigurationId) {
        ProjectSourceConfiguration configuration = repository.find(ProjectSourceConfigurationId.of(projectSourceConfigurationId))
                .orElseThrow(ProjectSourceConfigurationNotFoundException::new);

        Collection<DomainEvent> result = configuration.synchronize();
        messageBroker.publish(result);
        log.info("Projects synchronized for {}", projectSourceConfigurationId);
    }

    @Override
    public void removeProjectSourceConfiguration(String projectSourceConfigurationId) {
        ProjectSourceConfiguration configuration = repository.find(ProjectSourceConfigurationId.of(projectSourceConfigurationId))
                .orElseThrow(ProjectSourceConfigurationNotFoundException::new);

        Collection<DomainEvent> result = configuration.delete();
        repository.delete(ProjectSourceConfigurationId.of(projectSourceConfigurationId));
        messageBroker.publish(result);
    }

    @Override
    public Set<ReadProjectSourceConfigurationDto> listConfigurations() {
        return repository.findAll().stream()
                .map(projectSourceConfiguration -> projectSourceConfiguration.visitor(ReadProjectSourceConfigurationDto::new))
                .collect(Collectors.toSet());
    }

    @Override
    public Optional<ReadProjectSourceConfigurationDto> getConfiguration(String projectSourceConfigurationId) {
        return repository.find(ProjectSourceConfigurationId.of(projectSourceConfigurationId))
                .map(projectSourceConfiguration -> projectSourceConfiguration.visitor(ReadProjectSourceConfigurationDto::new));
    }

    @Override
    public Set<String> listSystemTypes() {
        return pluginQueryService.listSystemTypes();
    }

    @Override
    public Optional<ProjectPlugin> getPluginDefinition(String systemType) {
        return pluginQueryService.getPlugin(systemType);
    }

}
