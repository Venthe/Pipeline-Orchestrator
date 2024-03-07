package eu.venthe.pipeline.orchestrator._archive2.event_handlers;

import eu.venthe.pipeline.orchestrator._archive2.event_handlers.handlers.DefaultEventHandler;
import eu.venthe.pipeline.orchestrator._archive2.events.TriggerEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

import static com.google.common.collect.MoreCollectors.toOptional;

@Service
@RequiredArgsConstructor
public class EventHandlerProvider {
    private static final EventHandler DEFAULT_EVENT_HANDLER = new DefaultEventHandler();

    private final Set<TypedEventHandler> eventHandlers;

    public Optional<String> handle(TriggerEvent event) {
        return eventHandlers.stream()
                .filter(handler -> handler.discriminator().equals(event.getType()))
                .map(EventHandler.class::cast)
                .collect(toOptional())
                .orElse(DEFAULT_EVENT_HANDLER)
                .handle(event);
    }
}
