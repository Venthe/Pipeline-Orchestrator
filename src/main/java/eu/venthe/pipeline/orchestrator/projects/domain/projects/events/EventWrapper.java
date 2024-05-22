package eu.venthe.pipeline.orchestrator.projects.domain.projects.events;

import eu.venthe.pipeline.orchestrator.projects.domain.projects.workflows.contexts.on.matchers.*;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.SystemEvent;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.model.EventType;

public interface EventWrapper<T extends SystemEvent> {
    T getEvent();

    default Boolean matches(OnActivityType onTypes) {
        return true;
    }

    default Boolean matches(eu.venthe.pipeline.orchestrator.projects.domain.projects.workflows.contexts.on.matchers.OnWorkflowDispatchInputs onWorkflowDispatchInputs) {
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
