package eu.venthe.platform.workflow;

import eu.venthe.platform.shared_kernel.events.MessageListenerRegistry;
import eu.venthe.platform.shared_kernel.system_events.RepositoryEvent;
import eu.venthe.platform.workflow.handlers.EventHandler;
import org.springframework.stereotype.Service;

@SuppressWarnings("rawtypes")
@Service
public class RepositoryModuleMediatorListener {
    private RepositoryModuleMediatorListener(final EventHandler eventHandler, final MessageListenerRegistry registry) {
        registry.register(RepositoryEvent.class, new MessageListenerRegistry.Observer<>(this.getClass().getSimpleName(), envelope -> eventHandler.handle(envelope.getData())));
    }
}
