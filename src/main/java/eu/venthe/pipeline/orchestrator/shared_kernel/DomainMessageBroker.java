package eu.venthe.pipeline.orchestrator.shared_kernel;

import eu.venthe.pipeline.orchestrator.shared_kernel.events.DomainTrigger;

import java.util.Collection;

public interface DomainMessageBroker {
    void publish(Collection<DomainTrigger> events);

    void publish(DomainTrigger event);
}
