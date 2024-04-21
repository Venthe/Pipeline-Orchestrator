package eu.venthe.pipeline.orchestrator.shared_kernel.job_execution.contexts;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.pipeline.orchestrator.shared_kernel.version_control_events.contexts.utilities.ContextUtilities;

import java.util.HashMap;
import java.util.Map;

/**
 * The env context contains variables that have been set in a workflow, job, or step. It does not contain variables
 * inherited by the runner process. For more information about setting variables in your workflow, see "Workflow syntax
 * for GitHub Actions."
 * <p>
 * You can retrieve the values of variables stored in env context and use these values in your workflow file. You can
 * use the env context in any key in a workflow step except for the id and uses keys. For more information on the step
 * syntax, see "Workflow syntax for GitHub Actions."
 * <p>
 * If you want to use the value of a variable inside a runner, use the runner operating system's normal method for
 * reading environment variables.
 * <p>
 * This context changes for each step in a job. You can access this context from any step in a job. This object contains
 * the properties listed below.
 */
public class EnvContext {
    /**
     * The value of a specific environment variable.
     */
    private final Map<String, String> environmentVariables = new HashMap<>();

    public EnvContext(JsonNode _root) {
        ObjectNode root = ContextUtilities.validateIsObjectNode(_root);

        root.properties().forEach(e -> environmentVariables.put(e.getKey(), ContextUtilities.Text.ensure(e.getValue())));
    }

    public static EnvContext ensure(JsonNode env) {
        throw new UnsupportedOperationException();
    }
}
