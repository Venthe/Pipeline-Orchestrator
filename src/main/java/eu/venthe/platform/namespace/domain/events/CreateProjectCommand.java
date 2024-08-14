package eu.venthe.platform.namespace.domain.events;

import eu.venthe.platform.namespace.domain.NamespaceName;
import eu.venthe.platform.shared_kernel.events.DomainTrigger;
import eu.venthe.platform.source_configuration.domain.model.SourceOwnedProjectId;
import lombok.Value;

@Value
public class CreateProjectCommand implements DomainTrigger {
    NamespaceName namespaceName;
    SourceOwnedProjectId projectId;

    @Override
    public String getType() {
        return "create_project";
    }
}
