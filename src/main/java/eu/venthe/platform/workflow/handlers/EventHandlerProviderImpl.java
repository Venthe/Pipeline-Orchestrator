package eu.venthe.platform.workflow.handlers;

import com.google.common.collect.MoreCollectors;
import eu.venthe.platform.workflow.EventHandlerProvider;
import eu.venthe.platform.workflow.handlers.handlers.DefaultEventHandler;
import eu.venthe.platform.shared_kernel.events.DomainTrigger;
import eu.venthe.platform.shared_kernel.system_events.ProjectEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class EventHandlerProviderImpl implements EventHandlerProvider {
    @SuppressWarnings("rawtypes")
    private static final EventHandler DEFAULT_EVENT_HANDLER = new DefaultEventHandler();

    @SuppressWarnings("rawtypes")
    private final Set<EventHandler> eventHandlers;

    @Override
    public Collection<DomainTrigger> handle(ProjectEvent event) {
        //noinspection unchecked
        return eventHandlers.stream()
                .filter(e -> e.canHandle(event))
                .map(EventHandler.class::cast)
                .collect(MoreCollectors.toOptional())
                .orElse(DEFAULT_EVENT_HANDLER)
                .handle(event);
    }
}
