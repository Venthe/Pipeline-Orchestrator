package eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts;

import com.fasterxml.jackson.databind.JsonNode;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts.utilities.ContextUtilities;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.model.WorkflowState;
import lombok.experimental.UtilityClass;

// TODO: Implement context
@UtilityClass
public class WorkflowStateContext {
    public static WorkflowState ensure(final JsonNode state) {
        return ContextUtilities.ensure(state, ContextUtilities.fromTextMapper(WorkflowState::of)).orElseThrow();
    }
}