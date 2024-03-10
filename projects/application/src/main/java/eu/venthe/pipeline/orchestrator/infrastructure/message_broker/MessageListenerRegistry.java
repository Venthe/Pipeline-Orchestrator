package eu.venthe.pipeline.orchestrator.infrastructure.message_broker;

import java.util.function.Consumer;

public interface MessageListenerRegistry {
    void observe(Consumer<Envelope<?>> observer);
}
