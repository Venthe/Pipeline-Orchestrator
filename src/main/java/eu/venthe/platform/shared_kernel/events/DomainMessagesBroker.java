package eu.venthe.platform.shared_kernel.events;

import java.util.Collection;

public interface DomainMessagesBroker {
    void exchange(DomainMessage message);

    void exchange(Collection<DomainMessage> messages);
}
