package eu.venthe.platform.shared_kernel.system_events.contexts.model;

import com.fasterxml.jackson.databind.JsonNode;
import eu.venthe.platform.shared_kernel.system_events.contexts.utilities.ContextUtilities;
import eu.venthe.platform.shared_kernel.system_events.model.WorkflowState;
import lombok.experimental.UtilityClass;

@UtilityClass
public class WorkflowStateContext {
    public static WorkflowState ensure(final JsonNode state) {
        return ContextUtilities.ensure(state, ContextUtilities.fromTextMapper(WorkflowState::of)).orElseThrow();
    }
}
