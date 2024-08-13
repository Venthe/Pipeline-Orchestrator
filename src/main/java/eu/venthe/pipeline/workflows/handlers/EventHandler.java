package eu.venthe.pipeline.workflows.handlers;

import eu.venthe.pipeline.shared_kernel.events.DomainTrigger;
import eu.venthe.pipeline.shared_kernel.system_events.ProjectEvent;

import java.util.Collection;

public interface EventHandler<T extends ProjectEvent> {
    Collection<DomainTrigger> handle(T event);

    default boolean canHandle(T event) {
        throw new UnsupportedOperationException();
    }
}
