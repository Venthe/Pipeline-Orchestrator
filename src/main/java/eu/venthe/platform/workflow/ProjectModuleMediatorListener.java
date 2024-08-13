package eu.venthe.platform.workflow;

import eu.venthe.platform.application.modules.ProjectModuleMediator;
import eu.venthe.platform.shared_kernel.events.MessageListenerRegistry;
import eu.venthe.platform.shared_kernel.system_events.SystemEvent;
import org.springframework.stereotype.Service;

@Service
public class ProjectModuleMediatorListener {
    private ProjectModuleMediatorListener(final ProjectModuleMediator mediator,final MessageListenerRegistry registry) {
        registry.register(SystemEvent.class, new MessageListenerRegistry.Observer<>(this.getClass().getSimpleName(), envelope -> mediator.listen(envelope.getData())));
    }
}
