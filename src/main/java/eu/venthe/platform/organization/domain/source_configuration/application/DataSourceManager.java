package eu.venthe.platform.organization.domain.source_configuration.application;

import eu.venthe.platform.organization.domain.source_configuration.domain.ProjectsSourceConfiguration;
import eu.venthe.platform.organization.domain.source_configuration.domain.SourceConfigurationId;
import eu.venthe.platform.organization.domain.source_configuration.domain.infrastructure.DataSourceConfigurationRepository;
import eu.venthe.platform.organization.domain.source_configuration.plugins.PluginProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class DataSourceManager implements ProjectsSourceConfigurationQueryService, ProjectsSourceConfigurationCommandService {
    private final DataSourceConfigurationRepository repository;
    private final PluginProvider provider;

    @Override
    public SourceConfigurationId register(ProjectsSourceConfiguration.Specification specification) {

        if (repository.exists(specification.id())) {
            throw new IllegalArgumentException("Configuration \"%s\" already exists.".formatted(specification.id().id()));
        }

        var configuration = ProjectsSourceConfiguration.create(specification, provider);
        repository.save(configuration);

        return configuration.getConfigurationId();
    }

    @Override
    public void unregister(SourceConfigurationId sourceConfigurationId) {
        throw new UnsupportedOperationException();
    }
}
