package eu.venthe.pipeline.orchestrator.infrastructure.message_broker;

import java.util.function.Consumer;

public interface MessageListenerRegistry {
    <T> void observe(Class<T> clazz, Consumer<Envelope<T>> observer);
}
