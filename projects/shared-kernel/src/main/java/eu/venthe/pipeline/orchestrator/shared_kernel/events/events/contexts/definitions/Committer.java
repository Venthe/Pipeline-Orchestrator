package eu.venthe.pipeline.orchestrator.projects.domain.events.contexts.definitions;

import java.time.OffsetDateTime;
import java.util.Optional;

public interface Committer {
    /**
     * The git author's name.
     */
    String getName();

    /**
     * The git author's email address.
     */
    Optional<String> getEmail();

    Optional<OffsetDateTime> getDate();

    // Optional<String> getUsername();
}
