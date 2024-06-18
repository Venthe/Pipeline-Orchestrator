package eu.venthe.pipeline.orchestrator.modules;

import eu.venthe.pipeline.orchestrator.projects.domain.ProjectId;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.SystemEvent;

public interface ProjectModule {
    void handleEvent(final ProjectId id, SystemEvent event);

    void registerTrackedRef(ProjectId id, String ref);

    void unregisterTrackedRef(ProjectId id, String ref);
}
