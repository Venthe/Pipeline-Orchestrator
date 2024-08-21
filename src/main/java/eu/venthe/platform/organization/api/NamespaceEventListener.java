package eu.venthe.platform.organization.api;

import eu.venthe.platform.organization.application.OrganizationCommandService;
import eu.venthe.platform.organization.domain.events.OrganizationCreatedEvent;
import eu.venthe.platform.shared_kernel.events.MessageBroker;
import eu.venthe.platform.shared_kernel.events.MessageListenerRegistry;
import org.springframework.stereotype.Component;

@Component
public class OrganizationEventListener {
    public OrganizationEventListener(MessageListenerRegistry registry, OrganizationCommandService commandService) {
        registry.register(OrganizationCreatedEvent.class, env -> {
            commandService.synchronize(env.getData().getOrganizationName());
        });
    }
}
