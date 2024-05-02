package eu.venthe.pipeline.orchestrator.shared_kernel.job_execution.contexts;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.pipeline.orchestrator.shared_kernel.version_control_events.contexts.utilities.ContextUtilities;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static eu.venthe.pipeline.orchestrator.utilities.CollectionUtilities.sameKey;
import static eu.venthe.pipeline.orchestrator.utilities.CollectionUtilities.toMap;

/**
 * The inputs context contains input properties passed to an action, to a reusable workflow, or to a manually triggered
 * workflow. For reusable workflows, the input names and types are defined in the workflow_call event configuration of a
 * reusable workflow, and the input values are passed from jobs.<job_id>.with in an external workflow that calls the
 * reusable workflow. For manually triggered workflows, the inputs are defined in the workflow_dispatch event
 * configuration of a workflow.
 * <p>
 * The properties in the inputs context are defined in the workflow file. They are only available in a reusable workflow
 * or in a workflow triggered by the workflow_dispatch event
 */
@Getter
@EqualsAndHashCode
@RequiredArgsConstructor
public class InputsContext {
    /**
     * This context is only available in a reusable workflow or in a workflow triggered by the workflow_dispatch event.
     * You can access this context from any job or step in a workflow. This object contains the properties listed
     * below.
     * <p>
     * string or number or boolean or choice
     */
    @JsonAnyGetter
    private final Map<String, JsonNode> inputs = new HashMap<>();

    @JsonCreator
    private InputsContext(JsonNode _root) {
        ObjectNode root = ContextUtilities.validateIsObjectNode(_root);

        inputs.putAll(ContextUtilities.validateIsObjectNode(root).properties().stream()
                .map(sameKey(e -> ContextUtilities.ensure(e, ContextUtilities.BooleanTextualNumber::ensure)))
                .collect(toMap()));
    }

    public static Optional<InputsContext> create(JsonNode inputs) {
        return ContextUtilities.create(inputs, InputsContext::new);
    }
}
