package eu.venthe.platform.application.fixtures;

import eu.venthe.platform.source_configuration.domain.plugins.template.RepositorySourcePlugin;
import eu.venthe.platform.source_configuration.domain.plugins.template.RepositorySourcePluginInstance;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Consumer;

@RequiredArgsConstructor
@Slf4j
public class MockRepositorySourceFixture {
    private final MockRepositorySource instance;
    private final TestRepositorySourcePlugin sourcePlugin;

    public void onInstance(Consumer<MockRepositorySource> configure) {
        configure.accept(instance);
    }

    public interface TestRepositorySourcePlugin extends RepositorySourcePlugin {
    }

    public interface MockRepositorySource extends RepositorySourcePluginInstance {
    }
}
