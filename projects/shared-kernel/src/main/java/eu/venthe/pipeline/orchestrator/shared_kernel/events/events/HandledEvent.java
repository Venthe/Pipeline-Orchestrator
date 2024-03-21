package eu.venthe.pipeline.orchestrator.projects.domain.events;

import eu.venthe.pipeline.orchestrator.projects.domain.events.contexts.ActorContext;
import eu.venthe.pipeline.orchestrator.projects.domain.events.model.VersionControlEvent;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.events.EventType;
import eu.venthe.pipeline.orchestrator.projects.domain.workflows.contexts.on.*;

import java.util.UUID;

public interface HandledEvent extends VersionControlEvent {
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
