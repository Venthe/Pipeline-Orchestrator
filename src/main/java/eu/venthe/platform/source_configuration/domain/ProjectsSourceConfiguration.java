package eu.venthe.platform.source_configuration.domain;

import eu.venthe.platform.source_configuration.domain.plugins.template.ProjectSourcePlugin;
import eu.venthe.platform.source_configuration.domain.plugins.template.model.ProjectDto;
import eu.venthe.platform.source_configuration.plugins.template.ProjectSourcePlugin;
import eu.venthe.platform.project.application.ProjectsCommandService;
import eu.venthe.platform.project.application.ProjectsQueryService;
import eu.venthe.platform.source_configuration.plugins.template.model.ProjectDto;
import eu.venthe.platform.shared_kernel.Aggregate;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Optional;

@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ProjectsSourceConfiguration implements Aggregate<SourceConfigurationId> {
    @EqualsAndHashCode.Include
    @Getter
    private final SourceConfigurationId configurationId;
    @Getter
    private final ProjectSourcePlugin.PluginInstance pluginInstance;
    @ToString.Exclude
    private final ProjectsSourceConfigurationSynchronizer synchronizer;

    public static ProjectsSourceConfiguration createNew(SourceConfigurationId configurationId,
                                                        ProjectSourcePlugin.PluginInstance instance,
                                                        ProjectsCommandService projectsCommandService,
                                                        ProjectsQueryService projectsQueryService) {
        var projectsSourceConfiguration = reconstitute(configurationId, instance, projectsCommandService, projectsQueryService);
        // FIXME: Chicken and egg
        // projectsSourceConfiguration.synchronize(projectsCommandService, projectsQueryService);
        return projectsSourceConfiguration;
    }

    public static ProjectsSourceConfiguration reconstitute(SourceConfigurationId configurationId,
                                                           ProjectSourcePlugin.PluginInstance instance,
                                                           ProjectsCommandService projectsCommandService,
                                                           ProjectsQueryService projectsQueryService) {
        return new ProjectsSourceConfiguration(configurationId, instance, projectsCommandService, projectsQueryService);
    }

    private ProjectsSourceConfiguration(SourceConfigurationId configurationId,
                                        ProjectSourcePlugin.PluginInstance pluginInstance,
                                        ProjectsCommandService projectsCommandService,
                                        ProjectsQueryService projectsQueryService) {
        this.configurationId = configurationId;
        this.pluginInstance = pluginInstance;
        synchronizer = new ProjectsSourceConfigurationSynchronizer(this, pluginInstance, projectsCommandService, projectsQueryService);
    }

    public void synchronize() {
        synchronizer.synchronize();
    }

    public Optional<ProjectDto> getProject(String id) {
        return pluginInstance.getProject(id);
    }

    public SourceConfigurationId getId() {
        return configurationId;
    }
}
