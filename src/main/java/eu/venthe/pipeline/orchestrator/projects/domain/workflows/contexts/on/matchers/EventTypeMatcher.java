package eu.venthe.pipeline.orchestrator.projects.domain.workflows.contexts.on.matchers;

import com.fasterxml.jackson.databind.JsonNode;
import eu.venthe.pipeline.orchestrator.projects.domain.workflows.contexts.on.OnMatcher;
import eu.venthe.pipeline.orchestrator.projects.domain.events.EventWrapper;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.SystemEvent;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.contexts.utilities.ContextUtilities;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EventTypeMatcher implements OnMatcher {
    private final String eventType;

    public static EventTypeMatcher ensure(JsonNode root) {
        return new EventTypeMatcher(ContextUtilities.Text.ensure(root));
    }

    @Override
    public <T extends SystemEvent> Boolean on(EventWrapper<T> event) {
        return matchType(eventType, event.getType().getValue());
    }

    public static boolean matchType(String type, String otherType) {
        return type.equalsIgnoreCase(otherType);
    }
}
