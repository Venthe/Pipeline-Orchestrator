package eu.venthe.pipeline.orchestrator.shared_kernel.job_execution.contexts;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.pipeline.orchestrator.shared_kernel.version_control_events.contexts.utilities.ContextUtilities;

import java.util.HashMap;
import java.util.Map;

/**
 * The vars context contains custom configuration variables set at the organization, repository, and environment levels.
 * For more information about defining configuration variables for use in multiple workflows, see "Variables".
 */
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
