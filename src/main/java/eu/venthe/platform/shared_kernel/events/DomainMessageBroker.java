package eu.venthe.platform.shared_kernel.events;

import java.util.Collection;

public interface DomainMessageBroker {
    void publish(Collection<DomainTrigger> events);

    void publish(DomainTrigger event);
}
