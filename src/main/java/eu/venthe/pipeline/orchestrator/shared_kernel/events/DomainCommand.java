package eu.venthe.pipeline.orchestrator.shared_kernel.events;

public interface DomainCommand {
    default Integer getVersion() {
        return 1;
    }

    String getType();
}
