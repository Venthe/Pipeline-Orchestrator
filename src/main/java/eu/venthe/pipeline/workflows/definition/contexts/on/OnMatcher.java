package eu.venthe.pipeline.workflows.definition.contexts.on;

import eu.venthe.pipeline.workflows.events.EventWrapper;
import eu.venthe.pipeline.shared_kernel.system_events.SystemEvent;

public interface OnMatcher {
    <T extends SystemEvent> Boolean on(EventWrapper<T> event);
}
