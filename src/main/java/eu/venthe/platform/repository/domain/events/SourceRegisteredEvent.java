package eu.venthe.platform.repository.domain.events;

import eu.venthe.platform.shared_kernel.events.DomainMessage;

public record SourceRegisteredEvent(String sourceConfigurationName) implements DomainMessage {
    @Override
    public String getType() {
        return "source_configuration_registered";
    }
}
