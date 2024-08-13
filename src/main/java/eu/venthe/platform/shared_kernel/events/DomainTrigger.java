package eu.venthe.platform.shared_kernel.events;

public interface DomainTrigger {
    default Integer getVersion() {
        return 1;
    }

    String getType();
}
