package eu.venthe.pipeline.orchestrator.modules;

import eu.venthe.pipeline.orchestrator.shared_kernel.events.MessageListenerRegistry;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.SystemEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.function.Consumer;

@Slf4j
@Component
public class ProjectModuleMediator {
    private final Set<ProjectModule> modules;

    private ProjectModuleMediator(final Set<ProjectModule> modules, MessageListenerRegistry registry) {
        this.modules = modules;

        registry.register(SystemEvent.class, new MessageListenerRegistry.Observer<>(this.getClass().getSimpleName(), envelope -> listen(envelope.getData())));
    }

    public void onModule(Consumer<ProjectModule> action) {
        modules.forEach(action);
    }

    public void listen(SystemEvent event) {
        log.info("System event received. {}", event);
        onModule(module -> module.handleEvent(event));
    }
}
