package eu.venthe.platform.repository.domain.event;

import eu.venthe.platform.repository.domain.RepositoryName;
import eu.venthe.platform.shared_kernel.events.DomainTrigger;
import lombok.Value;

@Value
public class RepositoryCreatedEvent implements DomainTrigger {
    RepositoryName repositoryName;

    @Override
    public String getType() {
        return "repository_created_event";
    }
}
