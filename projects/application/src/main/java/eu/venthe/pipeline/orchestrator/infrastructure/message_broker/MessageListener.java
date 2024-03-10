package eu.venthe.pipeline.orchestrator.infrastructure.message_broker;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

@Slf4j
@Service
public class MessageListener implements MessageListenerRegistry {
    private final Collection<Consumer<Envelope<?>>> observers = new ArrayList<>();
    private final ExecutorService executorService = Executors.newFixedThreadPool(10);

    public void exchange(Envelope<?> envelope) {
        log.info("Exchanging envelope. {}", envelope);
        observers.forEach(observer -> executorService.execute(() -> {
            log.info("Envelope exchanged to {}", observer);
            observer.accept(envelope);
        }));
    }

    public void observe(Consumer<Envelope<?>> observer) {
        log.info("Registering observer {}", observer);
        observers.add(observer);
    }
}
