package eu.venthe.pipeline.orchestrator.modules;

import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.SystemEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.function.Consumer;

@RequiredArgsConstructor
@Component
public class ProjectModuleMediator {
    private final Set<ProjectModule> modules;

    public void onModule(Consumer<ProjectModule> action) {
        modules.forEach(action);
    }

    public void listen(SystemEvent event) {
        onModule(module -> module.handleEvent(event));
    }
}
