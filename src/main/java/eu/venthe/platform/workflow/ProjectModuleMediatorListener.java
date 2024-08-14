package eu.venthe.platform.workflow;

import eu.venthe.platform.shared_kernel.events.MessageListenerRegistry;
import eu.venthe.platform.shared_kernel.system_events.ProjectEvent;
import eu.venthe.platform.workflow.handlers.EventHandler;
import org.springframework.stereotype.Service;

@Service
public class ProjectModuleMediatorListener {
    private ProjectModuleMediatorListener(final EventHandler eventHandler, final MessageListenerRegistry registry) {
        registry.register(ProjectEvent.class, new MessageListenerRegistry.Observer<>(this.getClass().getSimpleName(), envelope -> eventHandler.handle(envelope.getData())));
    }
}
