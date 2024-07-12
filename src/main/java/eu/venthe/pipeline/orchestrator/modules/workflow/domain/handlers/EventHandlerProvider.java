package eu.venthe.pipeline.orchestrator.modules.workflow.domain.handlers;

import com.google.common.collect.MoreCollectors;
import eu.venthe.pipeline.orchestrator.modules.workflow.domain.handlers.handlers.DefaultEventHandler;
import eu.venthe.pipeline.orchestrator.projects.domain.ProjectSpecifiedDataProvider;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.DomainTrigger;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.ProjectEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class EventHandlerProvider {
    @SuppressWarnings("rawtypes")
    private static final EventHandler DEFAULT_EVENT_HANDLER = new DefaultEventHandler();

    @SuppressWarnings("rawtypes")
    private final Set<EventHandler> eventHandlers;

    public Collection<DomainTrigger> handle(final ProjectSpecifiedDataProvider provider, ProjectEvent event) {
        //noinspection unchecked
        return eventHandlers.stream()
                .filter(e -> e.canHandle(event))
                .map(EventHandler.class::cast)
                .collect(MoreCollectors.toOptional())
                .orElse(DEFAULT_EVENT_HANDLER)
                .handle(provider, event);
    }
}
