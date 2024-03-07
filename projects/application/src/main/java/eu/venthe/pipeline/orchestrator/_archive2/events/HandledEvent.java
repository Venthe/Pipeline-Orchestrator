package eu.venthe.pipeline.orchestrator._archive2.events;

import eu.venthe.pipeline.orchestrator._archive2.events.contexts.ActorContext;
import eu.venthe.pipeline.orchestrator._archive2.events.model.Event;
import eu.venthe.pipeline.orchestrator._archive2.events.model.EventType;
import eu.venthe.pipeline.orchestrator._archive2.workflows.contexts.on.*;

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
