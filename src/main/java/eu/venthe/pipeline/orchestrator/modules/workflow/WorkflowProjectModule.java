package eu.venthe.pipeline.orchestrator.modules.workflow;

import eu.venthe.pipeline.orchestrator.modules.ProjectModule;
import eu.venthe.pipeline.orchestrator.projects.domain.ProjectId;
import eu.venthe.pipeline.orchestrator.shared_kernel.git.Revision;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.SystemEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.togglz.core.manager.FeatureManager;
import org.togglz.core.util.NamedFeature;

@Component
@RequiredArgsConstructor
public class WorkflowProjectModule implements ProjectModule {
    private final FeatureManager featureManager;

    @Override
    public void handleEvent(final ProjectId id, final SystemEvent event) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void registerTrackedRevision(final ProjectId id, final Revision ref) {
        if (featureManager.isActive(new NamedFeature("GENERAL_WIP"))) {
            return;
        }
        throw new UnsupportedOperationException();
    }

    @Override
    public void unregisterTrackedRevision(final ProjectId id, final Revision ref) {
        throw new UnsupportedOperationException();
    }
}
