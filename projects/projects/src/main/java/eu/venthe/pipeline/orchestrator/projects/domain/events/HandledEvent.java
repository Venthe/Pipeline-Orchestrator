package eu.venthe.pipeline.orchestrator.projects.domain.events;

import eu.venthe.pipeline.orchestrator.projects.domain.events.contexts.ActorContext;
import eu.venthe.pipeline.orchestrator.projects.domain.events.model.Event;
import eu.venthe.pipeline.orchestrator.projects.domain.events.model.EventType;

import java.util.UUID;

public interface HandledEvent extends Event {
    EventType getType();

    UUID getId();

    ActorContext getActor();

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
}
