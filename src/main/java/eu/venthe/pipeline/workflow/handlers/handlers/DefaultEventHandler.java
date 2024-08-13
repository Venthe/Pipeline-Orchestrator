package eu.venthe.pipeline.workflow.handlers.handlers;

import eu.venthe.pipeline.shared_kernel.events.DomainTrigger;
import eu.venthe.pipeline.shared_kernel.system_events.ProjectEvent;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;

@Slf4j
public class DefaultEventHandler extends AbstractEventHandler<ProjectEvent> {
    @Override
    protected Collection<DomainTrigger> _handle(final ProjectEvent event) {
        log.warn("Using default event handler for event {}", event);
        throw new UnsupportedOperationException("This event is not handled " + event);
    }
}
