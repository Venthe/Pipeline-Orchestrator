package eu.venthe.pipeline.orchestrator.organizations.domain.projects.events;

import eu.venthe.pipeline.orchestrator.organizations.domain.domain.projects.domain.workflows.contexts.on.matchers.*;
import eu.venthe.pipeline.orchestrator.organizations.domain.projects.domain.workflows.contexts.on.matchers.*;
import eu.venthe.pipeline.orchestrator.organizations.domain.projects.workflows.contexts.on.matchers.OnWorkflowDispatchInputs;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.SystemEvent;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.model.EventType;

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