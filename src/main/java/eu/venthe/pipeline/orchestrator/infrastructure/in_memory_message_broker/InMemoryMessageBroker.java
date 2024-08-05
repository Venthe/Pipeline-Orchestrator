package eu.venthe.pipeline.orchestrator.infrastructure.in_memory_message_broker;

import eu.venthe.pipeline.orchestrator.shared_kernel.events.Envelope;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.MessageBroker;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.MessageListenerRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

@Slf4j
@Service
public class InMemoryMessageBroker implements MessageListenerRegistry, MessageBroker {
    private final Map<Class<?>, Collection<Observer<?>>> observers = new HashMap<>();
    private final ExecutorService executorService = Executors.newFixedThreadPool(10);

    public <T> void register(Class<T> clazz, Observer<T> observer) {
        log.info("Registering observer {} for class {}", observer, clazz);
        Collection<Observer<?>> consumers = observers.get(clazz);
        if (consumers == null) {
            ArrayList<Observer<?>> observersForClass = new ArrayList<>();
            observersForClass.add(observer);
            observers.put(clazz, observersForClass);
        } else {
            consumers.add(observer);
        }
    }

    @Override
    public <T> void register(final Class<T> clazz, final Consumer<Envelope<T>> consumer) {
        register(clazz, new Observer<>("Unknown " + UUID.randomUUID(), consumer));
    }

    public void exchange(Envelope<?> envelope) {
        log.info("Exchanging envelope. {}", envelope);
        Class<?> aClass = envelope.getData().getClass();
        getConsumers(aClass).forEach(observer -> executorService.execute(() -> {
            log.info("Envelope exchanged to {}", observer.name());
            acceptEnvelope(observer, envelope);
        }));
    }

    private <T> void acceptEnvelope(Observer<T> observer, Envelope<?> envelope) {
        Envelope<T> typedEnvelope = (Envelope<T>) envelope;
        observer.consumer().accept(typedEnvelope);
    }

    private Collection<Observer<?>> getConsumers(final Class<?> aClass) {
        return observers.keySet().stream()
                .filter(c -> c.isAssignableFrom(aClass))
                .map(observers::get)
                .flatMap(Collection::stream)
                .toList();
    }

    public void exchange(Collection<Envelope<?>> envelopes) {
        envelopes.forEach(this::exchange);
    }
}
