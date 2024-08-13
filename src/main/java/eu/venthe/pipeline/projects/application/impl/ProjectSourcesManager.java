package eu.venthe.pipeline.projects.application.impl;

import eu.venthe.pipeline.projects.application.ProjectsCommandService;
import eu.venthe.pipeline.projects.application.ProjectsQueryService;
import eu.venthe.pipeline.projects.application.ProjectsSourceConfigurationCommandService;
import eu.venthe.pipeline.projects.application.ProjectsSourceConfigurationQueryService;
import eu.venthe.pipeline.projects.domain.infrastructure.SourceConfigurationRepository;
import eu.venthe.pipeline.projects.domain.source_configurations.ProjectsSourceConfiguration;
import eu.venthe.pipeline.projects.domain.source_configurations.SourceConfigurationId;
import eu.venthe.pipeline.projects.domain.source_configurations.plugins.PluginProvider;
import eu.venthe.pipeline.projects.domain.source_configurations.plugins.template.model.SourceType;
import eu.venthe.pipeline.shared_kernel.configuration_properties.SuppliedProperties;
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
    public SourceConfigurationId register(SourceConfigurationId configurationId, SourceType sourceType, SuppliedProperties properties) {
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
    public void unregister(SourceConfigurationId configurationId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void synchronize(SourceConfigurationId configurationId) {
        var projectsSourceConfiguration = sources.find(configurationId).orElseThrow();
        projectsSourceConfiguration.synchronize();
    }

    @Override
    public void synchronizeAll() {
        throw new UnsupportedOperationException();
    }
}
