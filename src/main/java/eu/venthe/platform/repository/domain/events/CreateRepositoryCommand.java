package eu.venthe.platform.repository.domain.events;

import eu.venthe.platform.shared_kernel.events.DomainMessage;

public record CreateRepositoryCommand(String repositoryName, String sourceName) implements DomainMessage {

    @Override
    public String getType() {
        return "create_repository";
    }
}
