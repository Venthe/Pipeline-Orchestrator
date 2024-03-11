package eu.venthe.pipeline.orchestrator.infrastructure.message_broker;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

@Slf4j
@Service
public class MessageListener implements MessageListenerRegistry {
    private final Map<Class<?>, Collection<Consumer<Envelope<?>>>> observers = new HashMap<>();
    private final ExecutorService executorService = Executors.newFixedThreadPool(10);

    public void exchange(Envelope<?> envelope) {
        log.info("Exchanging envelope. {}", envelope);
        Class<?> aClass = envelope.getData().getClass();
        observers.get(aClass).forEach(observer -> executorService.execute(() -> {
            log.info("Envelope exchanged to {}", observer);
            observer.accept(envelope);
        }));
    }

    public <T> void observe(Class<T> clazz, Consumer<Envelope<T>> observer) {
        log.info("Registering observer {} for class {}", observer, clazz);
        Collection<Consumer<Envelope<?>>> consumers = observers.get(clazz);
        Consumer<Envelope<?>> var = (Consumer<Envelope<?>>) (Consumer<?>) observer;
        if (consumers == null) {
            ArrayList<Consumer<Envelope<?>>> value = new ArrayList<>();
            value.add(var);
            observers.put(clazz, value);
        } else {
            consumers.add(var);
        }
    }
}
