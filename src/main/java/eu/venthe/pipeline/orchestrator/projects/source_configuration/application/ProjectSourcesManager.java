package eu.venthe.pipeline.orchestrator.projects.source_configuration.application;

import eu.venthe.pipeline.orchestrator.projects.projects.application.ProjectsCommandService;
import eu.venthe.pipeline.orchestrator.projects.projects.application.ProjectsQueryService;
import eu.venthe.pipeline.orchestrator.projects.source_configuration.domain.ProjectsSourceConfiguration;
import eu.venthe.pipeline.orchestrator.projects.source_configuration.domain.ProjectsSourceConfigurationId;
import eu.venthe.pipeline.orchestrator.projects.source_configuration.domain.infrastructure.ProjectsSourceConfigurationRepository;
import eu.venthe.pipeline.orchestrator.projects.source_configuration.plugins.PluginProvider;
import eu.venthe.pipeline.orchestrator.projects.source_configuration.plugins.template.ProjectSourcePlugin;
import eu.venthe.pipeline.orchestrator.projects.source_configuration.plugins.template.model.SourceType;
import eu.venthe.pipeline.orchestrator.projects.source_configuration.plugins.template.model.SuppliedProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProjectSourcesManager {
    private final ProjectsSourceConfigurationRepository sources;
    private final PluginProvider pluginProvider;
    private final ProjectsCommandService projectsCommandService;
    private final ProjectsQueryService projectsQueryService;

    public void register(String _configurationId,
                         String _sourceType,
                         SuppliedProperties properties) {
        var configurationId = new ProjectsSourceConfigurationId(_configurationId);

        if (sources.exists(configurationId)) {
            log.warn("Project source configuration {} is already registered", configurationId);
            throw new IllegalArgumentException();
        }

        var sourceType = new SourceType(_sourceType);
        ProjectSourcePlugin.PluginInstance pluginInstance = pluginProvider.provide(sourceType, properties);

        ProjectsSourceConfiguration configuration = ProjectsSourceConfiguration.createNew(configurationId, pluginInstance, projectsCommandService, projectsQueryService);
        sources.save(configuration);
    }

    public void unregister(String _configurationId) {
        var configurationId = new ProjectsSourceConfigurationId(_configurationId);

        // TODO: Remove sources from the system
        //  remove all dependencies
        throw new UnsupportedOperationException();
    }

    public void synchronize(String _configurationId) {
        var configurationId = new ProjectsSourceConfigurationId(_configurationId);
        ProjectsSourceConfiguration projectsSourceConfiguration = sources.find(configurationId).orElseThrow();
        projectsSourceConfiguration.synchronize(projectsCommandService, projectsQueryService);
    }
}
