package eu.venthe.platform.organization.domain.events;

import eu.venthe.platform.shared_kernel.events.DomainTrigger;
import eu.venthe.platform.source_configuration.domain.model.SourceOwnedProjectId;

public class SynchronizeProjectCommand implements DomainTrigger {
    private final SourceOwnedProjectId projectId;

    public SynchronizeProjectCommand(final SourceOwnedProjectId projectId) {
        this.projectId = projectId;
    }

    @Override
    public String getType() {
        return "synchronize_project";
    }
}
