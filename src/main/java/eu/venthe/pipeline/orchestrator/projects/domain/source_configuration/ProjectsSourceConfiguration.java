package eu.venthe.pipeline.orchestrator.projects.domain.source_configuration;

import eu.venthe.pipeline.orchestrator.projects.application.ProjectsCommandService;
import eu.venthe.pipeline.orchestrator.projects.application.ProjectsQueryService;
import eu.venthe.pipeline.orchestrator.projects.domain.plugin_template.model.SourceType;
import eu.venthe.pipeline.orchestrator.projects.domain.plugin_template.PluginFactory;
import eu.venthe.pipeline.orchestrator.projects.domain.plugin_template.ProjectSourcePlugin;
import eu.venthe.pipeline.orchestrator.projects.domain.plugin_template.model.ProjectDto;
import eu.venthe.pipeline.orchestrator.projects.domain.plugin_template.model.SuppliedProperties;
import eu.venthe.pipeline.orchestrator.shared_kernel.Aggregate;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Optional;

@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ProjectsSourceConfiguration implements Aggregate<ProjectsSourceConfigurationId> {
    @EqualsAndHashCode.Include
    @Getter
    private final ProjectsSourceConfigurationId configurationId;
    private final ProjectSourcePlugin.PluginInstance pluginInstance;
    @ToString.Exclude
    private final ProjectsSourceConfigurationSynchronizer synchronizer;

    public static ProjectsSourceConfiguration createNew(ProjectsSourceConfigurationId configurationId,
                                                        SourceType sourceType,
                                                        SuppliedProperties properties,
                                                        PluginFactory pluginFactory,
                                                        ProjectsCommandService projectsCommandService,
                                                        ProjectsQueryService projectsQueryService) {
        var projectsSourceConfiguration = reconstitute(configurationId, sourceType, properties, pluginFactory);
        projectsSourceConfiguration.synchronize(projectsCommandService, projectsQueryService);
        return projectsSourceConfiguration;
    }

    public static ProjectsSourceConfiguration reconstitute(ProjectsSourceConfigurationId configurationId,
                                                           SourceType sourceType,
                                                           SuppliedProperties properties,
                                                           PluginFactory pluginFactory) {
        ProjectSourcePlugin.PluginInstance instance = pluginFactory.instantiate(sourceType, properties);
        return new ProjectsSourceConfiguration(configurationId, instance);
    }

    private ProjectsSourceConfiguration(ProjectsSourceConfigurationId configurationId, ProjectSourcePlugin.PluginInstance pluginInstance) {
        this.configurationId = configurationId;
        this.pluginInstance = pluginInstance;
        synchronizer = new ProjectsSourceConfigurationSynchronizer(this, pluginInstance);
    }

    public void synchronize(ProjectsCommandService projectsCommandService, ProjectsQueryService projectsQueryService) {
        synchronizer.synchronize(projectsCommandService, projectsQueryService);
    }

    public Optional<ProjectDto> getProject(String id) {
        return pluginInstance.getProject(id);
    }

    public ProjectsSourceConfigurationId getId() {
        return configurationId;
    }
}
