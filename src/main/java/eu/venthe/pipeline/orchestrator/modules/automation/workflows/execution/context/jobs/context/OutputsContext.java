package eu.venthe.pipeline.orchestrator.modules.automation.workflows.execution.context.jobs.context;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.contexts.utilities.ContextUtilities;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

// TODO: Implement context
public class OutputsContext {
    private final ObjectNode root;

    public OutputsContext(JsonNode root) {
        this.root = ContextUtilities.validateIsObjectNode(root);
    }

    public static Optional<OutputsContext> create(JsonNode root) {
        return ContextUtilities.create(root, OutputsContext::new);
    }

    public Map<String, String> getProperties() {
        return root.properties().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().asText()));
    }
}
