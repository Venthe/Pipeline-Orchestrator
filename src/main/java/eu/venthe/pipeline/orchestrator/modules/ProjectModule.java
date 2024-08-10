package eu.venthe.pipeline.orchestrator.modules;

import eu.venthe.pipeline.orchestrator.projects.domain.ProjectId;
import eu.venthe.pipeline.orchestrator.shared_kernel.git.GitRevision;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.SystemEvent;

public interface ProjectModule {
    void handleEvent(final SystemEvent event);

    void registerTrackedRevision(final ProjectId id, final GitRevision ref);

    void unregisterTrackedRevision(final ProjectId id, final GitRevision ref);
}
