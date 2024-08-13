package eu.venthe.pipeline.workflows.runs._archive.run_context.contexts;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.pipeline.shared_kernel.system_events.contexts.utilities.ContextUtilities;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Singular;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.HashMap;
import java.util.Map;

import static eu.venthe.pipeline.application.utilities.CollectionUtilities.sameKey;
import static eu.venthe.pipeline.application.utilities.CollectionUtilities.toMap;

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
@Getter
@ToString
@EqualsAndHashCode
@SuperBuilder
public class EnvContext {
    /**
     * The value of a specific environment variable.
     */
    @JsonAnyGetter
    @Singular
    private final Map<String, String> environmentVariables = new HashMap<>();

    @JsonCreator
    public EnvContext(JsonNode _root) {
        ObjectNode root = ContextUtilities.validateIsObjectNode(_root);

        environmentVariables.putAll(root.properties().stream().map(sameKey(ContextUtilities.Text::ensure)).collect(toMap()));
    }

    public static EnvContext ensure(JsonNode env) {
        return ContextUtilities.ensure(env, EnvContext::new);
    }
}


