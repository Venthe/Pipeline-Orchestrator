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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
public class Project implements Aggregate<ProjectId> {

    @EqualsAndHashCode.Include
    private final ProjectId id;
    private final ProjectsSourceConfiguration owningConfiguration;
    private final ProjectModuleMediator projectModules;

    private Optional<String> description;
    private ProjectStatus status;

    public void synchronize() {
        log.debug("Initiating synchronization of project {}", id);
        ProjectDto projectDto = owningConfiguration.getProject(getId().getName()).orElseThrow();
        description = projectDto.getDescription();
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

    public void handleEvent(SystemEvent event) {
        log.debug("Passing event {} from project {} for the modules", event.getType(), id);
        projectModules.onModule(module -> module.handleEvent(id, event));
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
