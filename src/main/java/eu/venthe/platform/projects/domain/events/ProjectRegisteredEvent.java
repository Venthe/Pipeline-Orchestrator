package eu.venthe.platform.projects.domain.events;

import eu.venthe.platform.shared_kernel.events.DomainMessage;

public record ProjectRegisteredEvent(String sourceConfigurationIdentifier, String projectName) implements DomainMessage {
    @Override
    public String getType() {
        return "project_registered";
    }
}
