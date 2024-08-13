package eu.venthe.platform.organization.domain.events;

import eu.venthe.platform.shared_kernel.events.DomainTrigger;

public class ArchiveProjectCommand implements DomainTrigger {
    @Override
    public String getType() {
        return "archive_project";
    }
}
