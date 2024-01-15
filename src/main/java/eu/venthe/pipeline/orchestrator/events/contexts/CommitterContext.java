package eu.venthe.pipeline.orchestrator.events.contexts;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.pipeline.orchestrator.events.contexts.common.DateTimeContext;
import eu.venthe.pipeline.orchestrator.events.contexts.definitions.Committer;
import eu.venthe.pipeline.orchestrator.utilities.ContextUtilities;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.Optional;

public class CommitterContext implements Committer {
    private final ObjectNode root;
    @Getter
    private final String name;
    @Getter
    private final Optional<String> email;
    @Getter
    private final Optional<OffsetDateTime> date;

    public CommitterContext(ObjectNode root) {
        this.root = root;

        name = ContextUtilities.toTextual(this.root.get("name")).orElseThrow(() -> new IllegalArgumentException("Name should be present"));
        email = ContextUtilities.toTextual(this.root.get("email"));
        date = DateTimeContext.create(this.root.get("date"));
    }

    public static Committer ensure(JsonNode commiter) {
        return Optional.ofNullable(commiter)
                .filter(JsonNode::isObject)
                .map(ObjectNode.class::cast)
                .map(CommitterContext::new)
                .orElseThrow();
    }
}
