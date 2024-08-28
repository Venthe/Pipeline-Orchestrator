package eu.venthe.platform.organization.domain.events;

import eu.venthe.platform.shared_kernel.events.DomainTrigger;
import eu.venthe.platform.source_configuration.domain.model.SourceOwnedRepositoryName;

public class SynchronizeRepositoryCommand implements DomainTrigger {
    private final SourceOwnedRepositoryName repositoryId;

    public SynchronizeRepositoryCommand(final SourceOwnedRepositoryName repositoryId) {
        this.repositoryId = repositoryId;
    }

    @Override
    public String getType() {
        return "synchronize_repository";
    }
}
