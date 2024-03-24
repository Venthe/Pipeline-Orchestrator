package eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts.git;

import com.fasterxml.jackson.databind.JsonNode;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts.common.EmailContext;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts.utilities.ContextUtilities;

import java.time.OffsetDateTime;
import java.util.Optional;

public final class ActorContext {
    /**
     * The git author's name.
     */
    private final String name;
    private final Optional<EmailContext> email;
    private final Optional<OffsetDateTime> date;
    private final Optional<String> username;

    private ActorContext(JsonNode author) {
        if (!author.isObject()) {
            throw new IllegalArgumentException();
        }

        name = ContextUtilities.ensure(author.get("name"), ContextUtilities.toTextMapper());
    }

    public static ActorContext ensure(JsonNode author) {
        return ContextUtilities.create(author, ActorContext::new)
                .orElseThrow(IllegalArgumentException::new);
    }
}
