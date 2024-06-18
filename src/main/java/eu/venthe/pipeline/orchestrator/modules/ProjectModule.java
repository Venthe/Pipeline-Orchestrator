package eu.venthe.pipeline.orchestrator.modules;

import eu.venthe.pipeline.orchestrator.projects.domain.ProjectId;
import eu.venthe.pipeline.orchestrator.shared_kernel.git.Revision;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.SystemEvent;

public interface ProjectModule {
    void handleEvent(final ProjectId id, SystemEvent event);

    void registerTrackedRevision(ProjectId id, Revision ref);

    void unregisterTrackedRevision(ProjectId id, Revision ref);
}
