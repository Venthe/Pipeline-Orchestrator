package eu.venthe.pipeline.orchestrator.shared_kernel.job_execution.contexts;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;

import java.util.Map;

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
@AllArgsConstructor
public class InputsContext {
    /**
     * This context is only available in a reusable workflow or in a workflow triggered by the workflow_dispatch event.
     * You can access this context from any job or step in a workflow. This object contains the properties listed
     * below.
     * <p>
     * string or number or boolean or choice
     */
    private final Map<String, ?> inputs;

    public static InputsContext ensure(JsonNode inputs) {
        throw new UnsupportedOperationException();
    }
}
