package eu.venthe.pipeline.orchestrator.modules.automation.workflows.definition.contexts.on;

import eu.venthe.pipeline.orchestrator.modules.automation.workflows.events.EventWrapper;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.SystemEvent;

public interface OnMatcher {
    <T extends SystemEvent> Boolean on(EventWrapper<T> event);
}
