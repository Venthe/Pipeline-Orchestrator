package eu.venthe.pipeline.orchestrator.shared_kernel.message_broker;

import java.util.function.Consumer;

public interface MessageListenerRegistry {
    <T> void register(Class<T> clazz, Consumer<Envelope<T>> observer);
}
