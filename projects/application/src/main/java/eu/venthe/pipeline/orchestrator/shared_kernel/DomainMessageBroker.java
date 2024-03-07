package eu.venthe.pipeline.orchestrator.shared_kernel;

import java.util.Collection;

public interface DomainMessageBroker {
    void publish(Collection<DomainEvent> events);
    void publish(DomainEvent event);
}
