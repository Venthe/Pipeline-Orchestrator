package eu.venthe.pipeline.shared_kernel.system_events;

import eu.venthe.pipeline.shared_kernel.system_events.contexts.RepositoryContext;

public interface ProjectEvent extends SystemEvent {

    RepositoryContext getRepository();
}
