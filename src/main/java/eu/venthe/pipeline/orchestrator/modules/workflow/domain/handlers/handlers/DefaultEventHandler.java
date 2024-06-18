package eu.venthe.pipeline.orchestrator.modules.workflow.domain.handlers.handlers;

import eu.venthe.pipeline.orchestrator.projects.domain.Project;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.DomainTrigger;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.SystemEvent;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;

@Slf4j
public class DefaultEventHandler extends AbstractEventHandler<SystemEvent> {
    @Override
    protected Collection<DomainTrigger> _handle(Project project, SystemEvent event) {
        log.warn("Using default event handler for event {}", event);
        throw new UnsupportedOperationException("This event is not handled " + event);
    }
}
