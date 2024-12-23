package eu.venthe.platform.projects.domain.events;

import eu.venthe.platform.shared_kernel.events.DomainMessage;

public record SynchronizeProjectsCommand(String sourceConfigurationIdentifier) implements DomainMessage {
    @Override
    public String getType() {
        return "synchronize_projects";
    }
}
