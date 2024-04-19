package eu.venthe.pipeline.orchestrator.projects.domain.workflows.contexts.on;

import com.fasterxml.jackson.databind.JsonNode;
import eu.venthe.pipeline.orchestrator.projects.domain.events.EventWrapper;
import eu.venthe.pipeline.orchestrator.projects.domain.workflows.contexts.on.matchers.EventTypeMatcher;
import eu.venthe.pipeline.orchestrator.projects.domain.workflows.contexts.on.matchers.OnMatcher;
import eu.venthe.pipeline.orchestrator.shared_kernel.version_control_events.ProjectEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RequiredArgsConstructor
@Slf4j
public class OnContext implements OnMatcher {
    private final JsonNode root;

    public static OnContext ensure(JsonNode root) {
        if (root == null || root.isNull()) {
            throw new IllegalArgumentException("There is no \"on\" property, this workflow will never run");
        }

        return new OnContext(root);
    }

    @Override
    public <T extends ProjectEvent> Boolean on(EventWrapper<T> event) {
        if (event == null) {
            throw new IllegalArgumentException("Eevent should not be null");
        }

        if (root.isTextual()) {
            log.info("Parsing single event {}", root);
            return EventTypeMatcher.ensure(root).on(event);
        } else if (root.isArray()) {
            log.info("Parsing array of events {}", root);

            if (StreamSupport.stream(root.spliterator(), false).collect(Collectors.toSet()).isEmpty()) {
                return false;
            }

            return StreamSupport.stream(root.spliterator(), false)
                    .map(EventTypeMatcher::ensure)
                    .map(e -> e.on(event))
                    .anyMatch(e -> e.equals(true));
        } else if (root.isObject()) {
            log.info("Parsing object of events {}", root);

            if (root.properties().isEmpty()) {
                return false;
            }

            return root.properties().stream()
                    .filter(e -> EventTypeMatcher.matchType(e.getKey(), event.getType().getValue()))
                    .map(v -> OnMatcherFactory.map(v.getKey(), v.getValue(), event))
                    .allMatch(e -> e.equals(true));
        }

        throw new UnsupportedOperationException();
    }
}
