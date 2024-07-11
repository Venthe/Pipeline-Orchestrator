package eu.venthe.pipeline.orchestrator.fixtures;

import eu.venthe.pipeline.orchestrator.projects.domain.source_configurations.plugins.template.ProjectSourcePlugin;
import eu.venthe.pipeline.orchestrator.projects.domain.source_configurations.plugins.template.model.SourceType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mockito.Mockito;

import java.util.function.Consumer;

import static org.mockito.ArgumentMatchers.any;

@RequiredArgsConstructor
@Slf4j
public class MockProjectSourceFixture {
    private final MockProjectSource instance;
    private final TestProjectSourcePlugin sourcePlugin;

    public void onSource(Consumer<MockProjectSource> configure) {
        configure.accept(instance);
    }

    public interface TestProjectSourcePlugin extends ProjectSourcePlugin {
    }

    public interface MockProjectSource extends ProjectSourcePlugin.PluginInstance {
    }
}
