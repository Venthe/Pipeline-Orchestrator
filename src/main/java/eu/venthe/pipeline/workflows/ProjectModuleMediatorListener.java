package eu.venthe.pipeline.workflows;

import eu.venthe.pipeline.orchestrator.modules.ProjectModuleMediator;
import eu.venthe.pipeline.shared_kernel.events.MessageListenerRegistry;
import eu.venthe.pipeline.shared_kernel.system_events.SystemEvent;
import org.springframework.stereotype.Service;

@Service
public class ProjectModuleMediatorListener {
    private ProjectModuleMediatorListener(final ProjectModuleMediator mediator,final MessageListenerRegistry registry) {
        registry.register(SystemEvent.class, new MessageListenerRegistry.Observer<>(this.getClass().getSimpleName(), envelope -> mediator.listen(envelope.getData())));
    }
}
