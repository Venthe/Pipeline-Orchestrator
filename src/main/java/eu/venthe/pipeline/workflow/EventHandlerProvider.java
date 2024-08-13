package eu.venthe.pipeline.workflow;

import eu.venthe.pipeline.shared_kernel.events.DomainTrigger;
import eu.venthe.pipeline.shared_kernel.system_events.ProjectEvent;

import java.util.Collection;

public interface EventHandlerProvider {
    Collection<DomainTrigger> handle(ProjectEvent event);
}
