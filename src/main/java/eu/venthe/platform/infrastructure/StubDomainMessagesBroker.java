package eu.venthe.platform.infrastructure;

import eu.venthe.platform.shared_kernel.events.DomainMessage;
import eu.venthe.platform.shared_kernel.events.DomainMessagesBroker;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;

@Slf4j
public class StubDomainMessagesBroker implements DomainMessagesBroker {
    @Override
    public void exchange(DomainMessage message) {
        log.warn("Invoking a stub for exchanging a message. Message={}", message);
    }

    @Override
    public void exchange(Collection<DomainMessage> messages) {
        log.warn("Invoking a stub for exchanging messages. Messages={}", messages);
    }
}
