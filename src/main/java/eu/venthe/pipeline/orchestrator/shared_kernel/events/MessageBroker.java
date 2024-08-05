package eu.venthe.pipeline.orchestrator.shared_kernel.events;

import java.util.Collection;

public interface MessageBroker {
    void exchange(Envelope<?> envelope);

    void exchange(Collection<Envelope<?>> envelopes);

}
