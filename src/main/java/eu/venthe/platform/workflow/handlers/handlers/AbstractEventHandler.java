package eu.venthe.platform.workflow.handlers.handlers;

import eu.venthe.platform.workflow.handlers.EventHandler;
import eu.venthe.platform.shared_kernel.events.DomainTrigger;
import eu.venthe.platform.shared_kernel.system_events.RepositoryEvent;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;

@SuppressWarnings("rawtypes")
@Slf4j
public abstract class AbstractEventHandler<T extends RepositoryEvent> implements EventHandler {
    @Override
    public final Collection<DomainTrigger> handle(RepositoryEvent event) {
        log.info("Handling event {} for repository {}", event, event.getRepository());
        if (!canHandle(event)) {
            throw new UnsupportedOperationException();
        }

        return _handle((T) event);
    }

    protected abstract Collection<DomainTrigger> _handle(T event);
}
