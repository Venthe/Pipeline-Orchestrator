package eu.venthe.pipeline.orchestrator.projects.domain.workflows.contexts;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.pipeline.orchestrator.shared_kernel.version_control_events.contexts.utilities.ContextUtilities;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class EnvironmentContext {
    private final JsonNode root;

    public static Optional<EnvironmentContext> create(JsonNode root) {
        return ContextUtilities.create(root, EnvironmentContext::new);
    }

    public Map<String, String> getProperties() {
        return root.properties().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().asText()));
    }
}
