package eu.venthe.pipeline.orchestrator.config;

import eu.venthe.pipeline.orchestrator.shared_kernel.message_broker.Envelope;
import eu.venthe.pipeline.orchestrator.shared_kernel.DomainMessageBroker;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.DomainTrigger;
import eu.venthe.pipeline.orchestrator.shared_kernel.message_broker.MessageBroker;
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
        broker.exchange(events.stream().map(Envelope::new).collect(Collectors.toSet()));
    }

    @Override
    public void publish(DomainTrigger event) {
        broker.exchange(new Envelope<>(event));
    }
}
