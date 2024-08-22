package eu.venthe.platform.shared_kernel.system_events.contexts.git;

import com.fasterxml.jackson.databind.JsonNode;
import eu.venthe.platform.shared_kernel.system_events.contexts.utilities.ContextUtilities;

// TODO: Finish actor context
public final class ActorContext {
    /**
     * The git author's name.
     */
    private final String name;
    // TODO: private final Optional<EmailContext> email;
    // TODO: private final Optional<OffsetDateTime> date;
    // TODO: private final Optional<String> username;

    private ActorContext(final JsonNode _root) {
        final var root = ContextUtilities.validateIsObjectNode(_root);

        name = ContextUtilities.ensure(root.get("name"), ContextUtilities.toText());
    }

    public static ActorContext ensure(final JsonNode author) {
        return ContextUtilities.create(author, ActorContext::new)
                .orElseThrow(IllegalArgumentException::new);
    }
}
