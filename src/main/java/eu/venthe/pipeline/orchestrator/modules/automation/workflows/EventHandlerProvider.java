package eu.venthe.pipeline.orchestrator.modules.automation.workflows;

import eu.venthe.pipeline.orchestrator.shared_kernel.events.DomainTrigger;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.ProjectEvent;

import java.util.Collection;

public interface EventHandlerProvider {
    Collection<DomainTrigger> handle(ProjectEvent event);
}
