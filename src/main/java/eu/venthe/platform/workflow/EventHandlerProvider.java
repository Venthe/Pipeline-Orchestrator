package eu.venthe.platform.workflow;

import eu.venthe.platform.shared_kernel.events.DomainTrigger;
import eu.venthe.platform.shared_kernel.system_events.RepositoryEvent;

import java.util.Collection;

public interface EventHandlerProvider {
    Collection<DomainTrigger> handle(RepositoryEvent event);
}
