package eu.venthe.platform.repository.domain.events;

import eu.venthe.platform.shared_kernel.events.DomainMessage;

public record SynchronizeRepositoriesCommand(String sourceName) implements DomainMessage {
    @Override
    public String getType() {
        return "synchronize_repositories";
    }
}
