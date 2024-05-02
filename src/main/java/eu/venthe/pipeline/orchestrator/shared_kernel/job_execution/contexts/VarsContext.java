package eu.venthe.pipeline.orchestrator.shared_kernel.job_execution.contexts;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.contexts.utilities.ContextUtilities;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

/**
 * The vars context contains custom configuration variables set at the organization, repository, and environment levels.
 * For more information about defining configuration variables for use in multiple workflows, see "Variables".
 */
@Getter
@ToString
@EqualsAndHashCode
public class VarsContext {
    @JsonAnyGetter
    private final Map<String, String> customConfigurationVariables = new HashMap<>();

    @JsonCreator
    public VarsContext(JsonNode _root) {
        ObjectNode root = ContextUtilities.validateIsObjectNode(_root);

        root.properties()
                .forEach(e -> customConfigurationVariables.put(e.getKey(), ContextUtilities.Text.ensure(e.getValue())));
    }

    public static VarsContext ensure(JsonNode vars) {
        return ContextUtilities.ensure(vars, VarsContext::new);
    }
}
