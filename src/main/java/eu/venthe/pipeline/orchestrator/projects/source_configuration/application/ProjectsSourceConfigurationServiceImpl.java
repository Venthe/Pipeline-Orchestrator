/*
package eu.venthe.pipeline.orchestrator.projects.source_configuration.application;

import eu.venthe.pipeline.orchestrator.security.annotations.IsProjectManager;
import eu.venthe.pipeline.orchestrator.security.annotations.IsSystemAdministrator;
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

    @IsSystemAdministrator
    @Override
    public String addProjectSourceConfiguration(String id, String sourceType, Map<String, String> properties) {
        Pair<ProjectSourceConfiguration, Collection<DomainEvent>> result = factory.createConfiguration(id, sourceType, properties);

        ProjectSourceConfiguration configuration = result.getLeft();
        repository.save(configuration);
        messageBroker.publish(result.getRight());

        log.info("Project source configuration added. {}, {}, {}", id, sourceType, properties);
        return configuration.getId().getValue();
    }

    @IsSystemAdministrator
    @IsProjectManager
    @Override
    public void synchronizeProject(String projectSourceConfigurationId) {
        ProjectSourceConfiguration configuration = repository.find(ProjectSourceConfigurationId.of(projectSourceConfigurationId))
                .orElseThrow(ProjectSourceConfigurationNotFoundException::new);

        Collection<DomainEvent> result = configuration.synchronize();
        messageBroker.publish(result);
        log.info("Projects synchronized for {}", projectSourceConfigurationId);
    }

    @IsSystemAdministrator
    @Override
    public void removeProjectSourceConfiguration(String projectSourceConfigurationId) {
        ProjectSourceConfiguration configuration = repository.find(ProjectSourceConfigurationId.of(projectSourceConfigurationId))
                .orElseThrow(ProjectSourceConfigurationNotFoundException::new);

        Collection<DomainEvent> result = configuration.delete();
        repository.delete(ProjectSourceConfigurationId.of(projectSourceConfigurationId));
        messageBroker.publish(result);
    }

    @IsSystemAdministrator
    @Override
    public void synchronizeProjects() {
        throw new UnsupportedOperationException("Not implemented");
    }

    @IsSystemAdministrator
    @Override
    public Set<ReadProjectSourceConfigurationDto> listConfigurations() {
        return repository.findAll().stream()
                .map(projectSourceConfiguration -> projectSourceConfiguration.visitor(ReadProjectSourceConfigurationDto::new))
                .collect(Collectors.toSet());
    }

    @IsSystemAdministrator
    @Override
    public Optional<ReadProjectSourceConfigurationDto> getConfiguration(String projectSourceConfigurationId) {
        return repository.find(ProjectSourceConfigurationId.of(projectSourceConfigurationId))
                .map(projectSourceConfiguration -> projectSourceConfiguration.visitor(ReadProjectSourceConfigurationDto::new));
    }

    @IsSystemAdministrator
    @Override
    public Set<String> listSystemTypes() {
        return pluginQueryService.listSystemTypes();
    }

    @IsSystemAdministrator
    @Override
    public Optional<ProjectSourceAdapter> getPluginDefinition(String systemType) {
        return pluginQueryService.getPlugin(systemType);
    }

}
*/
