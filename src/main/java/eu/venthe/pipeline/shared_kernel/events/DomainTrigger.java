package eu.venthe.pipeline.shared_kernel.events;

public interface DomainTrigger {
    default Integer getVersion() {
        return 1;
    }

    String getType();
}
