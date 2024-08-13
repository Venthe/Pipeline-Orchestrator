package eu.venthe.platform.application.fixtures;

import eu.venthe.platform.data_source_configuration.plugins.template.ProjectSourcePlugin;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Consumer;

@RequiredArgsConstructor
@Slf4j
public class MockProjectSourceFixture {
    private final MockProjectSource instance;
    private final TestProjectSourcePlugin sourcePlugin;

    public void onInstance(Consumer<MockProjectSource> configure) {
        configure.accept(instance);
    }

    public interface TestProjectSourcePlugin extends ProjectSourcePlugin {
    }

    public interface MockProjectSource extends ProjectSourcePlugin.PluginInstance {
    }
}
