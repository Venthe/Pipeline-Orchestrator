package eu.venthe.platform.project.domain;

import eu.venthe.platform.shared_kernel.Aggregate;
import eu.venthe.platform.shared_kernel.git.GitRevision;
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

    public void registerTrackedRevision(GitRevision revision) {
        log.debug("Notify about registered ref {} in the project {} for the modules", revision, id);
    }

    public void unregisterTrackedRevision(GitRevision revision) {
        log.debug("Notify about unregistered ref {} in the project {} for the modules", revision, id);
    }

}
