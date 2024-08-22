package eu.venthe.platform.source_configuration.domain.plugins.template;

import java.util.Optional;
import java.util.stream.Stream;

public interface RepositoryProvider {
    Stream<Repository> getRepository();

    Stream<SourceRepositoryId> getRepositoryIdentifiers();

    Optional<Repository> getRepository(SourceRepositoryId id);
}
