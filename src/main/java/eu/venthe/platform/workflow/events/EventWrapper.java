package eu.venthe.platform.workflow.events;

import eu.venthe.platform.application.modules.automation.workflows.definition.contexts.on.matchers.*;
import eu.venthe.platform.shared_kernel.system_events.SystemEvent;
import eu.venthe.platform.shared_kernel.system_events.model.EventType;
import eu.venthe.platform.workflow.definition.contexts.on.matchers.*;

public interface EventWrapper<T extends SystemEvent> {
    T getEvent();

    default Boolean matches(OnActivityType onTypes) {
        return true;
    }

    default Boolean matches(OnWorkflowDispatchInputs onWorkflowDispatchInputs) {
        return true;
    }

    default Boolean matches(OnWorkflowCallInputs onWorkflowCallInputs) {
        return true;
    }

    default Boolean matches(OnBranches onBranches) {
        return true;
    }

    default Boolean matches(OnPaths onPaths) {
        return true;
    }

    default Boolean matches(OnTags onTags) {
        return true;
    }

    String getId();

    EventType getType();
}
