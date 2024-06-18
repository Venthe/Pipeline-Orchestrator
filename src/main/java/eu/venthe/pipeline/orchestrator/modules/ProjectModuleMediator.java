package eu.venthe.pipeline.orchestrator.modules;

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
}
