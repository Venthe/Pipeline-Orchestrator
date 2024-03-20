package eu.venthe.pipeline.orchestrator.projects.domain.event_handlers;

import com.google.common.collect.MoreCollectors;
import eu.venthe.pipeline.orchestrator.projects.api.Event;
import eu.venthe.pipeline.orchestrator.projects.domain.Project;
import eu.venthe.pipeline.orchestrator.projects.domain.event_handlers.handlers.DefaultEventHandler;
import eu.venthe.pipeline.orchestrator.shared_kernel.DomainEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class EventHandlerProvider {
    private static final EventHandler DEFAULT_EVENT_HANDLER = new DefaultEventHandler();

    private final Set<EventHandler> eventHandlers;

    public Collection<DomainEvent> handle(Project project, Event event) {
        return eventHandlers.stream()
                .filter(e -> e.canHandle(event))
                .map(EventHandler.class::cast)
                .collect(MoreCollectors.toOptional())
                .orElse(DEFAULT_EVENT_HANDLER)
                .handle(project, event);
    }
}
