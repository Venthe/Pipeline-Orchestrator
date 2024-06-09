package eu.venthe.pipeline.orchestrator.organizations.application;

import eu.venthe.pipeline.orchestrator.organizations.domain.infrastructure.SourceConfigurationRepository;
import eu.venthe.pipeline.orchestrator.organizations.domain.source_configurations.ProjectsSourceConfiguration;
import eu.venthe.pipeline.orchestrator.organizations.domain.source_configurations.ProjectsSourceConfigurationId;
import eu.venthe.pipeline.orchestrator.organizations.domain.source_configurations.plugins.PluginProvider;
import eu.venthe.pipeline.orchestrator.organizations.domain.source_configurations.plugins.template.ProjectSourcePlugin;
import eu.venthe.pipeline.orchestrator.organizations.domain.source_configurations.plugins.template.model.SourceType;
import eu.venthe.pipeline.orchestrator.shared_kernel.configuration_properties.SuppliedProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProjectSourcesManager implements ProjectsSourceConfigurationCommandService, ProjectsSourceConfigurationQueryService {
    private final SourceConfigurationRepository sources;
    private final PluginProvider pluginProvider;
    private final ProjectsCommandService projectsCommandService;
    private final ProjectsQueryService projectsQueryService;

    @Override
    public ProjectsSourceConfigurationId register(ProjectsSourceConfigurationId configurationId, SourceType sourceType, SuppliedProperties properties) {
        if (sources.exists(configurationId)) {
            log.warn("Project source configuration {} is already registered", configurationId);
            throw new IllegalArgumentException();
        }

        var pluginInstance = pluginProvider.provide(sourceType, properties);

        var configuration = ProjectsSourceConfiguration.createNew(configurationId, pluginInstance, projectsCommandService, projectsQueryService);
        sources.save(configuration);
        return configuration.getId();
    }

    @Override
    public void unregister(ProjectsSourceConfigurationId configurationId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void synchronize(ProjectsSourceConfigurationId configurationId) {
        var projectsSourceConfiguration = sources.find(configurationId).orElseThrow();
        projectsSourceConfiguration.synchronize();
    }

    @Override
    public void synchronizeAll() {
        throw new UnsupportedOperationException();
    }
}
