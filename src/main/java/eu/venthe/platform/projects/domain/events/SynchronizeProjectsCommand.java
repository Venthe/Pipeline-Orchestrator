package eu.venthe.platform.projects.domain.events;

import eu.venthe.platform.projects.domain.SourceConfigurationInternalIdentifier;
import eu.venthe.platform.shared_kernel.events.DomainMessage;

public record SynchronizeProjectsCommand(SourceConfigurationInternalIdentifier sourceConfigurationInternalIdentifier) implements DomainMessage {
    @Override
    public String getType() {
        return "synchronize_projects";
    }
}
