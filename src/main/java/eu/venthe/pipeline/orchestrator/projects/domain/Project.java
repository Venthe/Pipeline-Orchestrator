package eu.venthe.pipeline.orchestrator.projects.domain;

import eu.venthe.pipeline.orchestrator.modules.ProjectModuleMediator;
import eu.venthe.pipeline.orchestrator.projects.domain.source_configurations.ProjectsSourceConfiguration;
import eu.venthe.pipeline.orchestrator.projects.domain.source_configurations.plugins.template.model.ProjectDto;
import eu.venthe.pipeline.orchestrator.shared_kernel.Aggregate;
import eu.venthe.pipeline.orchestrator.shared_kernel.git.Revision;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.SystemEvent;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
public class Project implements Aggregate<ProjectId> {

    @EqualsAndHashCode.Include
    private final ProjectId id;
    private final ProjectsSourceConfiguration owningConfiguration;
    private final ProjectModuleMediator projectModules;
    private final ProjectSpecifiedDataProvider provider;

    public Project(final ProjectId id,
                    final ProjectsSourceConfiguration owningConfiguration,
                    final ProjectModuleMediator projectModules) {
        this.id = id;
        this.owningConfiguration = owningConfiguration;
        this.projectModules = projectModules;
        this.provider = new ProjectSpecifiedDataProvider(this.id.getName(), owningConfiguration.getPluginInstance());
    }

    private ProjectStatus status;

    public void synchronize() {
        log.debug("Initiating synchronization of project {}", id);
        ProjectDto projectDto = owningConfiguration.getProject(getId().getName()).orElseThrow();
        status = projectDto.getStatus();
        log.info("Synchronization of project {} done", id);
    }

    public void makeUnavailable() {
        status = ProjectStatus.NOT_AVAILABLE;
    }

    public void archive() {
        status = ProjectStatus.ARCHIVED;
    }

    public void makePublic() {
        status = ProjectStatus.ARCHIVED;
    }

    public void registerTrackedRevision(Revision revision) {
        log.debug("Notify about registered ref {} in the project {} for the modules", revision, id);
        projectModules.onModule(module -> module.registerTrackedRevision(id, revision));
    }

    public void unregisterTrackedRevision(Revision revision) {
        log.debug("Notify about unregistered ref {} in the project {} for the modules", revision, id);
        projectModules.onModule(module -> module.unregisterTrackedRevision(id, revision));
    }

}
