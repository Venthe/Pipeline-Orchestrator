package eu.venthe.platform.organization.domain.events;

import eu.venthe.platform.shared_kernel.events.DomainTrigger;

public class SynchronizeProjectCommand implements DomainTrigger {
    @Override
    public String getType() {
        return "synchronize_project";
    }
}
