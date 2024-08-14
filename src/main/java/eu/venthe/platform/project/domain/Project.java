package eu.venthe.platform.project.domain;

import eu.venthe.platform.project.domain.event.ProjectCreatedEvent;
import eu.venthe.platform.shared_kernel.Aggregate;
import eu.venthe.platform.shared_kernel.events.DomainTrigger;
import eu.venthe.platform.shared_kernel.git.GitRevision;
import eu.venthe.platform.shared_kernel.git.SimpleRevision;
import eu.venthe.platform.shared_kernel.io.File;
import eu.venthe.platform.shared_kernel.project.ProjectStatus;
import eu.venthe.platform.source_configuration.application.SourceQueryService;
import eu.venthe.platform.source_configuration.domain.model.ConfigurationSourceId;
import eu.venthe.platform.source_configuration.domain.model.SourceOwnedProject;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;

import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Project implements Aggregate<ProjectId> {

    @EqualsAndHashCode.Include
    private final ProjectId id;
    private final Source source;
    private ProjectStatus status;

    public Project(final ProjectId id,
                   final ConfigurationSourceId configurationSourceId,
                   final SourceQueryService sourceQueryService) {
        this.id = id;
        this.source = new Source(sourceQueryService, configurationSourceId, id);
    }

    public static Pair<Project, List<DomainTrigger>> create(final ProjectId projectId, final ConfigurationSourceId configurationSourceId, final SourceQueryService sourceQueryService) {
        return Pair.of(new Project(projectId, configurationSourceId, sourceQueryService), Collections.singletonList(new ProjectCreatedEvent(projectId)));
    }

    public void synchronize() {
        log.debug("Initiating synchronization of project {}", id);
        var freshProjectInfo = source.getProject();
        status = freshProjectInfo.status();
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

    public Optional<File> getFile(final SimpleRevision revision, final Path file) {
        return getSource().getFile(revision, file);
    }

    @AllArgsConstructor
    @ToString
    static final class Source {
        @ToString.Exclude
        private final SourceQueryService sourceQueryService;
        private final ConfigurationSourceId configurationSourceId;
        private final ProjectId projectId;

        eu.venthe.platform.source_configuration.domain.plugins.template.Project getProject() {
            return sourceQueryService.getProject(configurationSourceId, projectId.getName())
                    .map(SourceOwnedProject::project)
                    .orElseThrow();
        }

        private Optional<File> getFile(final SimpleRevision revision, final Path file) {
            return sourceQueryService.getFile(configurationSourceId, projectId.getName(), revision, file);
        }
    }
}
