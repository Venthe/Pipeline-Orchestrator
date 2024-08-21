package eu.venthe.platform.organization.domain.events;

import eu.venthe.platform.organization.domain.OrganizationName;
import eu.venthe.platform.shared_kernel.events.DomainTrigger;
import eu.venthe.platform.source_configuration.domain.model.SourceOwnedProjectId;
import lombok.Value;

@Value
public class CreateProjectCommand implements DomainTrigger {
    OrganizationName organizationName;
    SourceOwnedProjectId projectId;

    @Override
    public String getType() {
        return "create_project";
    }
}
