package eu.venthe.pipeline.orchestrator.shared_kernel.job_execution.contexts;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.pipeline.orchestrator.shared_kernel.version_control_events.contexts.utilities.ContextUtilities;

import java.util.HashMap;
import java.util.Map;

public class VarsContext {
    private final Map<String, String> customConfigurationVariables = new HashMap<>();

    public VarsContext(JsonNode _root) {
        ObjectNode root = ContextUtilities.validateIsObjectNode(_root);

        root.properties().forEach(e -> customConfigurationVariables.put(e.getKey(), ContextUtilities.Text.ensure(e.getValue())));
    }

    public static VarsContext ensure(JsonNode vars) {
        throw new UnsupportedOperationException();
    }
}
