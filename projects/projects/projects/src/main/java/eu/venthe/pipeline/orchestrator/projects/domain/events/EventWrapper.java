package eu.venthe.pipeline.orchestrator.projects.domain.events;

import eu.venthe.pipeline.orchestrator.projects.domain.workflows.contexts.on.matchers.on.*;
import eu.venthe.pipeline.orchestrator.shared_kernel.version_control_events.ProjectEvent;
import eu.venthe.pipeline.orchestrator.shared_kernel.version_control_events.model.EventType;

public interface EventWrapper<T extends ProjectEvent> {
    T getEvent();

    default Boolean matches(OnTypes onTypes) {
        return true;
    }

    default Boolean matches(OnInputs onInputs) {
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
