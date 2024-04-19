package eu.venthe.pipeline.orchestrator.shared_kernel.job_execution.contexts;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.pipeline.orchestrator.shared_kernel.version_control_events.contexts.utilities.ContextUtilities;

import java.util.HashMap;
import java.util.Map;

public class EnvContext {
    private final Map<String, String> environmentVariables = new HashMap<>();

    public EnvContext(JsonNode _root) {
        ObjectNode root = ContextUtilities.validateIsObjectNode(_root);

        root.properties().forEach(e -> environmentVariables.put(e.getKey(), ContextUtilities.Text.ensure(e.getValue())));
    }

    public static EnvContext ensure(JsonNode env) {
        throw new UnsupportedOperationException();
    }
}
