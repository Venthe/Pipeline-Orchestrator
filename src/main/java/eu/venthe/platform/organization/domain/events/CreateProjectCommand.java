package eu.venthe.platform.organization.domain.events;

import eu.venthe.platform.shared_kernel.events.DomainTrigger;
import eu.venthe.platform.source_configuration.domain.model.SourceOwnedProject;
import eu.venthe.platform.source_configuration.domain.model.SourceOwnedProjectId;

public class CreateProjectCommand implements DomainTrigger {
    private final SourceOwnedProjectId projectId;

    public CreateProjectCommand(final SourceOwnedProjectId project) {
        this.projectId = project;
    }

    @Override
    public String getType() {
        return "create_project";
    }
}
