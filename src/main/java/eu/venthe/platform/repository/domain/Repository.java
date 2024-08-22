package eu.venthe.platform.repository.domain;

import eu.venthe.platform.repository.domain.event.RepositoryCreatedEvent;
import eu.venthe.platform.shared_kernel.Aggregate;
import eu.venthe.platform.shared_kernel.events.DomainTrigger;
import eu.venthe.platform.shared_kernel.git.GitRevision;
import eu.venthe.platform.shared_kernel.git.SimpleRevision;
import eu.venthe.platform.shared_kernel.io.File;
import eu.venthe.platform.shared_kernel.repository.RepositoryStatus;
import eu.venthe.platform.source_configuration.application.SourceQueryService;
import eu.venthe.platform.source_configuration.domain.model.ConfigurationSourceId;
import eu.venthe.platform.source_configuration.domain.model.SourceOwnedRepository;
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
public class Repository implements Aggregate<RepositoryId> {

    @EqualsAndHashCode.Include
    private final RepositoryId id;
    private final Source source;
    private RepositoryStatus status;

    public Repository(final RepositoryId id,
                   final ConfigurationSourceId configurationSourceId,
                   final SourceQueryService sourceQueryService) {
        this.id = id;
        this.source = new Source(sourceQueryService, configurationSourceId, id);
    }

    public static Pair<Repository, List<DomainTrigger>> create(final RepositoryId repositoryId, final ConfigurationSourceId configurationSourceId, final SourceQueryService sourceQueryService) {
        return Pair.of(new Repository(repositoryId, configurationSourceId, sourceQueryService), Collections.singletonList(new RepositoryCreatedEvent(repositoryId)));
    }

    public void synchronize() {
        log.debug("Initiating synchronization of repository {}", id);
        var freshRepositoryInfo = source.getRepository();
        status = freshRepositoryInfo.status();
        log.info("Synchronization of repository {} done", id);
    }

    public void makeUnavailable() {
        status = RepositoryStatus.NOT_AVAILABLE;
    }

    public void archive() {
        status = RepositoryStatus.ARCHIVED;
    }

    public void makePublic() {
        status = RepositoryStatus.ARCHIVED;
    }

    public void registerTrackedRevision(GitRevision revision) {
        log.debug("Notify about registered ref {} in the repository {} for the modules", revision, id);
    }

    public void unregisterTrackedRevision(GitRevision revision) {
        log.debug("Notify about unregistered ref {} in the repository {} for the modules", revision, id);
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
        private final RepositoryId repositoryId;

        eu.venthe.platform.source_configuration.domain.plugins.template.Repository getRepository() {
            return sourceQueryService.getRepository(configurationSourceId, repositoryId.name())
                    .map(SourceOwnedRepository::repository)
                    .orElseThrow();
        }

        private Optional<File> getFile(final SimpleRevision revision, final Path file) {
            return sourceQueryService.getFile(configurationSourceId, repositoryId.name(), revision, file);
        }
    }
}
