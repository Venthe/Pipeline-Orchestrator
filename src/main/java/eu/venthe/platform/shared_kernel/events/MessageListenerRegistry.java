package eu.venthe.platform.shared_kernel.events;

import java.util.function.Consumer;

public interface MessageListenerRegistry {
    <T> void register(Class<T> clazz, Observer<T> observer);

    <T> void register(Class<T> clazz, Consumer<Envelope<T>> consumer);

    record Observer<T>(String name, Consumer<Envelope<T>> consumer) {
    }
}
