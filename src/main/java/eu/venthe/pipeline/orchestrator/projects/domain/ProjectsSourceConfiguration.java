package eu.venthe.pipeline.orchestrator.projects.domain;

import eu.venthe.pipeline.orchestrator.projects.application.ProjectsCommandService;
import eu.venthe.pipeline.orchestrator.projects.application.ProjectsQueryService;
import eu.venthe.pipeline.orchestrator.projects.domain.infrastructure.ProjectRepository;
import eu.venthe.pipeline.orchestrator.projects.domain.plugin_template.ProjectSourcePlugin;
import eu.venthe.pipeline.orchestrator.projects.domain.plugin_template.model.ProjectDto;
import eu.venthe.pipeline.orchestrator.shared_kernel.Aggregate;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.Optional;

@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@RequiredArgsConstructor
public class ProjectsSourceConfiguration implements Aggregate<ProjectsSourceConfigurationId> {
    @EqualsAndHashCode.Include
    @Getter
    private final ProjectsSourceConfigurationId configurationId;
    private final ProjectSourcePlugin.PluginInstance pluginInstance;
    private final ProjectsSourceConfigurationSynchronizer synchronizer = new ProjectsSourceConfigurationSynchronizer(this, pluginInstance);

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
