package eu.venthe.platform.shared_kernel.system_events;

import eu.venthe.platform.shared_kernel.system_events.contexts.RepositoryContext;

public interface ProjectEvent extends SystemEvent {

    RepositoryContext getRepository();
}
