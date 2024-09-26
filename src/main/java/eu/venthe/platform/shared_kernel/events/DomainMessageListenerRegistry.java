package eu.venthe.platform.shared_kernel.events;

import java.util.function.Consumer;

public interface DomainMessageListenerRegistry {
    <T extends DomainMessage> void register(String messageType, Consumer<T> consumer);
}
