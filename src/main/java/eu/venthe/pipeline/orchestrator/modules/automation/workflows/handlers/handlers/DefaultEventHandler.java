package eu.venthe.pipeline.orchestrator.modules.automation.workflows.handlers.handlers;

import eu.venthe.pipeline.orchestrator.projects.domain.ProjectSpecifiedDataProvider;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.DomainTrigger;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.ProjectEvent;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.List;

@Slf4j
public class DefaultEventHandler extends AbstractEventHandler<ProjectEvent> {
    @Override
    protected Collection<DomainTrigger> _handle(final ProjectEvent event) {
        log.warn("Using default event handler for event {}", event);
        throw new UnsupportedOperationException("This event is not handled " + event);
    }
}
