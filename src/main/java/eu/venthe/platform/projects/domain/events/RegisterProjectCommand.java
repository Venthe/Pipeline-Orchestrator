package eu.venthe.platform.projects.domain.events;

import eu.venthe.platform.shared_kernel.events.DomainMessage;

public record RegisterProjectCommand(String sourceName, String projectName) implements DomainMessage {

    @Override
    public String getType() {
        return "register_project";
    }
}
