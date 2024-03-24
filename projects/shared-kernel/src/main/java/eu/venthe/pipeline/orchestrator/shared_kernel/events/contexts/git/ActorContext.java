package eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts.git;

import com.fasterxml.jackson.databind.JsonNode;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts.common.EmailContext;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts.utilities.ContextUtilities;

import java.time.OffsetDateTime;
import java.util.Optional;

// TODO: Finish actor context
@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public final class ActorContext {
    /**
     * The git author's name.
     */
    private final String name;
    private final Optional<EmailContext> email;
    private final Optional<OffsetDateTime> date;
    private final Optional<String> username;

    private ActorContext(final JsonNode _root) {
        final var root = ContextUtilities.validateIsObjectNode(_root);

        name = ContextUtilities.ensure(root.get("name"), ContextUtilities.toTextMapper());
        
        throw new UnsupportedOperationException();
    }

    public static ActorContext ensure(final JsonNode author) {
        return ContextUtilities.create(author, ActorContext::new)
                .orElseThrow(IllegalArgumentException::new);
    }
}
