package eu.venthe.platform.shared_kernel.system_events;

import eu.venthe.platform.shared_kernel.system_events.contexts.RepositoryContext;

public interface RepositoryEvent extends SystemEvent {

    RepositoryContext getRepository();
}
