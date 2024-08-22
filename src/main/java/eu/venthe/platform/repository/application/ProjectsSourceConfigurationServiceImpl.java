/*
package eu.venthe.pipeline.orchestrator.repositorys.source_configuration.application;

import eu.venthe.pipeline.orchestrator.security.annotations.IsRepositoryManager;
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
public class RepositorySourceConfigurationServiceImpl implements RepositorySourceConfigurationCommandService, RepositorySourceConfigurationQueryService {
    private final RepositorySourceRepository repository;
    private final DomainMessageBroker messageBroker;
    private final RepositorySourceConfigurationFactory factory;
    private final RepositorySourcePluginQueryService pluginQueryService;

    @IsSystemAdministrator
    @Override
    public String addRepositorySourceConfiguration(String id, String sourceType, Map<String, String> properties) {
        Pair<RepositorySourceConfiguration, Collection<DomainEvent>> result = factory.createConfiguration(id, sourceType, properties);

        RepositorySourceConfiguration configuration = result.getLeft();
        repository.save(configuration);
        messageBroker.publish(result.getRight());

        log.info("Repository source configuration added. {}, {}, {}", id, sourceType, properties);
        return configuration.getId().getValue();
    }

    @IsSystemAdministrator
    @IsRepositoryManager
    @Override
    public void synchronizeRepository(String repositorySourceConfigurationId) {
        RepositorySourceConfiguration configuration = repository.find(RepositorySourceConfigurationId.of(repositorySourceConfigurationId))
                .orElseThrow(RepositorySourceConfigurationNotFoundException::new);

        Collection<DomainEvent> result = configuration.synchronize();
        messageBroker.publish(result);
        log.info("Repository synchronized for {}", repositorySourceConfigurationId);
    }

    @IsSystemAdministrator
    @Override
    public void removeRepositorySourceConfiguration(String repositorySourceConfigurationId) {
        RepositorySourceConfiguration configuration = repository.find(RepositorySourceConfigurationId.of(repositorySourceConfigurationId))
                .orElseThrow(RepositorySourceConfigurationNotFoundException::new);

        Collection<DomainEvent> result = configuration.delete();
        repository.delete(RepositorySourceConfigurationId.of(repositorySourceConfigurationId));
        messageBroker.publish(result);
    }

    @IsSystemAdministrator
    @Override
    public void synchronizeRepository() {
        throw new UnsupportedOperationException("Not implemented");
    }

    @IsSystemAdministrator
    @Override
    public Set<ReadRepositorySourceConfigurationDto> listConfigurations() {
        return repository.findAll().stream()
                .map(repositorySourceConfiguration -> repositorySourceConfiguration.visitor(ReadRepositorySourceConfigurationDto::new))
                .collect(Collectors.toSet());
    }

    @IsSystemAdministrator
    @Override
    public Optional<ReadRepositorySourceConfigurationDto> getConfiguration(String repositorySourceConfigurationId) {
        return repository.find(RepositorySourceConfigurationId.of(repositorySourceConfigurationId))
                .map(repositorySourceConfiguration -> repositorySourceConfiguration.visitor(ReadRepositorySourceConfigurationDto::new));
    }

    @IsSystemAdministrator
    @Override
    public Set<String> listSystemTypes() {
        return pluginQueryService.listSystemTypes();
    }

    @IsSystemAdministrator
    @Override
    public Optional<RepositorySourceAdapter> getPluginDefinition(String systemType) {
        return pluginQueryService.getPlugin(systemType);
    }

}
*/
