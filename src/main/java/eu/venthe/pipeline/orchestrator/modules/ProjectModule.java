package eu.venthe.pipeline.orchestrator.modules;

import eu.venthe.pipeline.orchestrator.projects.domain.ProjectSpecifiedDataProvider;
import eu.venthe.pipeline.orchestrator.projects.domain.ProjectId;
import eu.venthe.pipeline.orchestrator.shared_kernel.git.Revision;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.SystemEvent;

public interface ProjectModule {
    void handleEvent(final ProjectSpecifiedDataProvider provider, final SystemEvent event);

    void registerTrackedRevision(final ProjectId id, final Revision ref);

    void unregisterTrackedRevision(final ProjectId id, final Revision ref);
}
