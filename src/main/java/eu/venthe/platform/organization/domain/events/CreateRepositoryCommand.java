package eu.venthe.platform.organization.domain.events;

import eu.venthe.platform.organization.domain.OrganizationName;
import eu.venthe.platform.shared_kernel.events.DomainTrigger;
import eu.venthe.platform.source_configuration.domain.model.SourceOwnedRepositoryName;
import lombok.Value;

@Value
public class CreateRepositoryCommand implements DomainTrigger {
    OrganizationName organizationName;
    SourceOwnedRepositoryName repositoryId;

    @Override
    public String getType() {
        return "create_repository";
    }
}
