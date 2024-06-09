package eu.venthe.pipeline.orchestrator.shared_kernel.message_broker;

import java.util.Collection;

public interface MessageBroker {
    void exchange(Envelope<?> envelope);

    void exchange(Collection<Envelope<?>> envelopes);
}
