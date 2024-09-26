package eu.venthe.platform.infrastructure;

import eu.venthe.platform.shared_kernel.events.DomainMessage;
import eu.venthe.platform.shared_kernel.events.DomainMessageListenerRegistry;
import eu.venthe.platform.shared_kernel.events.DomainMessagesBroker;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

@Slf4j
public class InMemoryDomainMessageBroker implements DomainMessageListenerRegistry, DomainMessagesBroker {
    private final Map<String, Collection<Consumer<DomainMessage>>> observers = new HashMap<>();
    private final ExecutorService executorService = Executors.newFixedThreadPool(10);

    @Override
    public <T extends DomainMessage> void register(String messageType, Consumer<T> consumer) {
        log.debug("Registering observer for message type {}", messageType);
        Optional.ofNullable(observers.get(messageType)).ifPresentOrElse(
                consumers -> consumers.add((Consumer<DomainMessage>) consumer),
                () -> {
                    ArrayList<Consumer<DomainMessage>> newObservers = new ArrayList<>();
                    newObservers.add((Consumer<DomainMessage>) consumer);
                    observers.put(messageType, newObservers);
                }
        );
    }

    @Override
    public void exchange(DomainMessage message) {
        log.debug("Exchanging message. {}", message);
        Optional.ofNullable(observers.get(message.getType()))
                .ifPresent(observersForType ->
                        observersForType.forEach(observer -> executorService.execute(() -> observer.accept(message)))
                );
    }

    @Override
    public void exchange(Collection<DomainMessage> messages) {
        messages.forEach(this::exchange);
    }
}
