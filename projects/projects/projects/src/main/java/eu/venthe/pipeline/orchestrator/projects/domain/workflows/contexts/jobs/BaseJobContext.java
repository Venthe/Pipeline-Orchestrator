package eu.venthe.pipeline.orchestrator.projects.domain.workflows.contexts.jobs;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.pipeline.orchestrator.shared_kernel.version_control_events.contexts.utilities.ContextUtilities;
import eu.venthe.pipeline.orchestrator.utilities.CollectionUtilities;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@RequiredArgsConstructor
public class BaseJobContext {
    private final ObjectNode root;

    public static BaseJobContext ensure(JsonNode root) {
        return new BaseJobContext(ContextUtilities.validateIsObjectNode(root));
    }

    public List<String> getNeeds() {
        Optional<JsonNode> _needs = Optional.ofNullable(this.root.get("needs"));

        if (_needs.isEmpty()) return Collections.emptyList();
        JsonNode jsonNode = _needs.get();

        if (jsonNode.isArray()) {
            return CollectionUtilities.iteratorToStream(jsonNode.elements()).map(JsonNode::asText).toList();
        }

        if (jsonNode.isTextual()) {
            return List.of(jsonNode.asText());
        }

        throw new UnsupportedOperationException();
    }

    public ObjectNode getRaw() {
        return (ObjectNode) root;
    }

    public <T> T specify(Function<ObjectNode, T> mapper) {
        return mapper.apply(root);
    }
}
