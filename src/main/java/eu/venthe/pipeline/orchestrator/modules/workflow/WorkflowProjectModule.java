package eu.venthe.pipeline.orchestrator.modules.workflow;

import eu.venthe.pipeline.orchestrator.modules.ProjectModule;
import eu.venthe.pipeline.orchestrator.projects.domain.ProjectId;
import eu.venthe.pipeline.orchestrator.shared_kernel.git.Revision;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.SystemEvent;
import org.springframework.stereotype.Component;

@Component
public class WorkflowProjectModule implements ProjectModule {
    @Override
    public void handleEvent(final ProjectId id, final SystemEvent event) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void registerTrackedRevision(final ProjectId id, final Revision ref) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void unregisterTrackedRevision(final ProjectId id, final Revision ref) {
        throw new UnsupportedOperationException();
    }
}
