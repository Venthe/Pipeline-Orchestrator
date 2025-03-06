package eu.venthe.platform.projects.domain.events;

import eu.venthe.platform.projects.domain.ProjectCorrelationId;
import eu.venthe.platform.projects.domain.SourceConfigurationInternalIdentifier;
import eu.venthe.platform.shared_kernel.events.DomainMessage;

public record RegisterProjectCommand(
        SourceConfigurationInternalIdentifier sourceConfigurationInternalIdentifier,
        ProjectCorrelationId projectCorrelationId
) implements DomainMessage {

    public static final String REGISTER_PROJECT = "register_project";

    @Override
    public String getType() {
        return REGISTER_PROJECT;
    }
}
