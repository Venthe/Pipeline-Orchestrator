package eu.venthe.platform.organization.domain.events;

import eu.venthe.platform.shared_kernel.events.DomainTrigger;
import eu.venthe.platform.source_configuration.domain.model.SourceOwnedProjectId;

public class ArchiveProjectCommand implements DomainTrigger {
    private final SourceOwnedProjectId projectId;

    public ArchiveProjectCommand(final SourceOwnedProjectId projectId) {
        this.projectId = projectId;
    }

    @Override
    public String getType() {
        return "archive_project";
    }
}
