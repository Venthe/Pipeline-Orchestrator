package eu.venthe.platform.projects.domain.events;

import eu.venthe.platform.projects.domain.ProjectCorrelationId;
import eu.venthe.platform.projects.domain.SourceConfigurationInternalIdentifier;
import eu.venthe.platform.shared_kernel.events.DomainMessage;

public record ProjectRegisteredEvent(SourceConfigurationInternalIdentifier sourceConfigurationIdentifier, ProjectCorrelationId projectCorrelationId) implements DomainMessage {
    @Override
    public String getType() {
        return "project_registered";
    }
}
