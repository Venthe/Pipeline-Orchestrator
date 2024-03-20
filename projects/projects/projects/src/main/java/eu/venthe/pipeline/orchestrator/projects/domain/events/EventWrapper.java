package eu.venthe.pipeline.orchestrator.projects.domain.events;

import eu.venthe.pipeline.orchestrator.projects.api.Event;
import eu.venthe.pipeline.orchestrator.projects.domain.workflows.contexts.on.*;

public interface EventWrapper<T extends Event> {
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

    String getType();
}
