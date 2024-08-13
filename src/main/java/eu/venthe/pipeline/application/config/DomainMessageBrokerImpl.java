package eu.venthe.pipeline.application.config;

import eu.venthe.pipeline.shared_kernel.events.DomainMessageBroker;
import eu.venthe.pipeline.shared_kernel.events.DomainTrigger;
import eu.venthe.pipeline.shared_kernel.events.Envelope;
import eu.venthe.pipeline.shared_kernel.events.MessageBroker;
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
        Collection<Envelope<?>> collect = events.stream().map(Envelope::new).collect(Collectors.toSet());
        broker.exchange(collect);
    }

    @Override
    public void publish(DomainTrigger event) {
        broker.exchange(new Envelope<>(event));
    }
}
