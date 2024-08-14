package eu.venthe.platform.namespace.api;

import eu.venthe.platform.namespace.application.NamespaceCommandService;
import eu.venthe.platform.namespace.domain.events.NamespaceCreatedEvent;
import eu.venthe.platform.shared_kernel.events.MessageBroker;
import eu.venthe.platform.shared_kernel.events.MessageListenerRegistry;
import org.springframework.stereotype.Component;

@Component
public class NamespaceEventListener {
    public NamespaceEventListener(MessageListenerRegistry registry, NamespaceCommandService commandService) {
        registry.register(NamespaceCreatedEvent.class, env -> {
            commandService.synchronize(env.getData().getNamespaceName());
        });
    }
}
