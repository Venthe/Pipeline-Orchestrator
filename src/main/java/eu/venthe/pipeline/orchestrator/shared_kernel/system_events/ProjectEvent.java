package eu.venthe.pipeline.orchestrator.shared_kernel.system_events;

import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.contexts.RepositoryContext;

public interface ProjectEvent extends SystemEvent {

    RepositoryContext getRepository();
}
