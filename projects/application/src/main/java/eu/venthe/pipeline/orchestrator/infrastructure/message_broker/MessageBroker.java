package eu.venthe.pipeline.orchestrator.infrastructure.message_broker;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageBroker {
    private final MessageListener messageListener;

    public void publish(Envelope<?> envelope) {
        log.info("Publishing a message to broker. {}", envelope);
        messageListener.exchange(envelope);
    }

    public void publishAll(Collection<Envelope<?>> envelopes) {
        log.info("Publishing a messages to broker. {}", envelopes);
        envelopes.forEach(messageListener::exchange);
    }
}
