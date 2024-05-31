package eu.venthe.pipeline.orchestrator.projects.domain.projects.workflows.contexts.on;

import eu.venthe.pipeline.orchestrator.projects.domain.projects.events.EventWrapper;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.SystemEvent;

public interface OnMatcher {
    <T extends SystemEvent> Boolean on(EventWrapper<T> event);
}
