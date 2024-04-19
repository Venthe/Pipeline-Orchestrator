package eu.venthe.pipeline.orchestrator.projects.domain.workflows.contexts.on.matchers;

import eu.venthe.pipeline.orchestrator.projects.domain.events.EventWrapper;
import eu.venthe.pipeline.orchestrator.shared_kernel.version_control_events.ProjectEvent;

public interface OnMatcher {
    <T extends ProjectEvent> Boolean on(EventWrapper<T> event);
}
