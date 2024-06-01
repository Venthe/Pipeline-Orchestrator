package eu.venthe.pipeline.orchestrator.projects.source_configuration.domain;

import eu.venthe.pipeline.orchestrator.projects.projects.application.ProjectsCommandService;
import eu.venthe.pipeline.orchestrator.projects.projects.application.ProjectsQueryService;
import eu.venthe.pipeline.orchestrator.projects.source_configuration.plugins.template.ProjectSourcePlugin;
import eu.venthe.pipeline.orchestrator.projects.source_configuration.plugins.template.model.ProjectDto;
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
    private final ProjectsCommandService projectsCommandService;
    @ToString.Exclude
    private final ProjectsQueryService projectsQueryService;
    @ToString.Exclude
    private final ProjectsSourceConfigurationSynchronizer synchronizer;

    public static ProjectsSourceConfiguration createNew(ProjectsSourceConfigurationId configurationId,
                                                        ProjectSourcePlugin.PluginInstance instance,
                                                        ProjectsCommandService projectsCommandService,
                                                        ProjectsQueryService projectsQueryService) {
        var projectsSourceConfiguration = reconstitute(configurationId, instance, projectsCommandService, projectsQueryService);
        // FIXME
        // projectsSourceConfiguration.synchronize(projectsCommandService, projectsQueryService);
        return projectsSourceConfiguration;
    }

    public static ProjectsSourceConfiguration reconstitute(ProjectsSourceConfigurationId configurationId,
                                                           ProjectSourcePlugin.PluginInstance instance,
                                                           ProjectsCommandService projectsCommandService,
                                                           ProjectsQueryService projectsQueryService) {
        return new ProjectsSourceConfiguration(configurationId, instance, projectsCommandService, projectsQueryService);
    }

    private ProjectsSourceConfiguration(ProjectsSourceConfigurationId configurationId,
                                        ProjectSourcePlugin.PluginInstance pluginInstance,
                                        ProjectsCommandService projectsCommandService,
                                        ProjectsQueryService projectsQueryService) {
        this.configurationId = configurationId;
        this.pluginInstance = pluginInstance;
        synchronizer = new ProjectsSourceConfigurationSynchronizer(this, pluginInstance);
        this.projectsCommandService = projectsCommandService;
        this.projectsQueryService = projectsQueryService;
    }

    public void synchronize() {
        synchronizer.synchronize(projectsCommandService, projectsQueryService);
    }

    public Optional<ProjectDto> getProject(String id) {
        return pluginInstance.getProject(id);
    }

    public ProjectsSourceConfigurationId getId() {
        return configurationId;
    }
}
