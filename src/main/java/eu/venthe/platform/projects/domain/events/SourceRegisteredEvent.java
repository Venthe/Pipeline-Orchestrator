package eu.venthe.platform.projects.domain.events;

import eu.venthe.platform.projects.domain.SourceConfigurationInternalIdentifier;
import eu.venthe.platform.shared_kernel.events.DomainMessage;

public record SourceRegisteredEvent(SourceConfigurationInternalIdentifier sourceConfigurationIdentifier) implements DomainMessage {
    @Override
    public String getType() {
        return "source_configuration_registered";
    }
}
