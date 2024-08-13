package eu.venthe.platform.workflow.definition.contexts.on;

import eu.venthe.platform.workflow.events.EventWrapper;
import eu.venthe.platform.shared_kernel.system_events.SystemEvent;

public interface OnMatcher {
    <T extends SystemEvent> Boolean on(EventWrapper<T> event);
}
