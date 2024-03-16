package eu.venthe.pipeline.orchestrator.projects.application;

import eu.venthe.pipeline.orchestrator._archive2.event_handlers.EventHandlerProvider;
import eu.venthe.pipeline.orchestrator._archive2.events.TriggerEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
// TODO: Add event.getId() as a span for this action
@Slf4j
@RequiredArgsConstructor
public class WorkflowTriggerService {
    private final EventHandlerProvider eventHandlerProvider;

    public Optional<String> trigger(TriggerEvent event) {
        log.info("Received event to be handled. Type={}, EventId={}", event.getType(), event.getId());

        return eventHandlerProvider.handle(event);
    }
}
