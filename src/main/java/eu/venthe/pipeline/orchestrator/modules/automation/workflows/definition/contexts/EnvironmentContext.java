package eu.venthe.pipeline.orchestrator.modules.automation.workflows.definition.contexts;

import com.fasterxml.jackson.databind.JsonNode;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.contexts.utilities.ContextUtilities;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.Optional;

// TODO: Implement context
@RequiredArgsConstructor
public class EnvironmentContext {
    private EnvironmentContext(JsonNode node) {
    }

    public static Optional<EnvironmentContext> create(JsonNode root) {
        return ContextUtilities.create(root, EnvironmentContext::new);
    }

    public Map<String, String> getProperties() {
        throw new UnsupportedOperationException();
    }
}
