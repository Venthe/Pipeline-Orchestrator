package eu.venthe.pipeline.shared_kernel.events;

import java.util.Collection;

public interface MessageBroker {
    void exchange(Envelope<?> envelope);

    void exchange(Collection<Envelope<?>> envelopes);

}
