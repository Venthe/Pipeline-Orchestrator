package eu.venthe.pipeline.orchestrator.projects.projects.domain;

import eu.venthe.pipeline.orchestrator.projects.domain.projects.events.handlers.EventHandlerProvider;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.DomainTrigger;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.SystemEvent;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.function.Function;

@RequiredArgsConstructor
public class ProjectEventListener {
    private final Project project;

    public Function<EventHandlerProvider, Collection<DomainTrigger>> handleEvent(SystemEvent event) {
        return eventHandlerProvider -> eventHandlerProvider.handle(project, event);
    }
}
