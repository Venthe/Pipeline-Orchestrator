package eu.venthe.pipeline.orchestrator.shared_kernel;

import eu.venthe.pipeline.orchestrator.shared_kernel.events.DomainEvent;

import java.util.Collection;

public interface DomainMessageBroker {
    void publish(Collection<DomainEvent> events);

    void publish(DomainEvent event);
}
