package eu.venthe.pipeline.orchestrator.projects.domain.events.contexts;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.pipeline.orchestrator.projects.domain.events.contexts.definitions.UserLite;
import eu.venthe.pipeline.orchestrator.projects.domain.utilities.ContextUtilities;
import lombok.Getter;

import java.util.Optional;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public final class ActorContext implements UserLite {
    private final ObjectNode root;
    @Getter
    private final String login;

    private ActorContext(ObjectNode root) {
        this.root = root;

        login = ContextUtilities.toTextual(this.root.get("login")).orElseThrow();
    }

    public static Optional<ActorContext> create(JsonNode root) {
        return ContextUtilities.get(ActorContext::new, root);
    }

    public static ActorContext ensure(JsonNode root) {
        return create(root).orElseThrow(() -> new IllegalArgumentException("Sender must be present"));
    }
}
