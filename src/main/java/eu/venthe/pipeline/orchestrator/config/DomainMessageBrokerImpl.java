package eu.venthe.pipeline.orchestrator.config;

import eu.venthe.pipeline.orchestrator.config.infrastructure.in_memory_message_broker.EnvelopeImpl;
import eu.venthe.pipeline.orchestrator.config.infrastructure.in_memory_message_broker.MessageBroker;
import eu.venthe.pipeline.orchestrator.shared_kernel.DomainMessageBroker;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.DomainTrigger;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class DomainMessageBrokerImpl implements DomainMessageBroker {
    private final MessageBroker broker;

    @Override
    public void publish(Collection<DomainTrigger> events) {
        broker.publishAll(events.stream().map(EnvelopeImpl::new).collect(Collectors.toSet()));
    }

    @Override
    public void publish(DomainTrigger event) {
        broker.publish(new EnvelopeImpl<>(event));
    }
}
