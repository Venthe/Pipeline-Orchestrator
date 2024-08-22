package eu.venthe.platform.application.configuration;

import eu.venthe.platform.application.fixtures.MockAdapterFixture;
import eu.venthe.platform.application.fixtures.MockRepositorySourceFixture;
import eu.venthe.platform.runner.runner_engine.template.model.RunnerEngineType;
import eu.venthe.platform.source_configuration.domain.model.SourceType;
import lombok.extern.slf4j.Slf4j;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import static org.mockito.ArgumentMatchers.any;

@Slf4j
@TestConfiguration
public class MockBeanConfiguration {

    @Bean
    MockAdapterFixture.MockRunnerEngineInstance adapterInstance() {
        return Mockito.mock(MockAdapterFixture.MockRunnerEngineInstance.class);
    }

    @Bean
    MockAdapterFixture.TestRunnerEngineDefinition executionAdapter(MockAdapterFixture.MockRunnerEngineInstance adapterInstance) {
        var executionAdapter = Mockito.mock(MockAdapterFixture.TestRunnerEngineDefinition.class);
        Mockito.when(executionAdapter.getEngineType()).thenReturn(new RunnerEngineType("default"));
        Mockito.when(executionAdapter.instantiate(any())).thenReturn(adapterInstance);
        return executionAdapter;
    }

    @Bean
    MockAdapterFixture mockAdapterFixture(MockAdapterFixture.MockRunnerEngineInstance adapterInstance, MockAdapterFixture.TestRunnerEngineDefinition adapter) {
        return new MockAdapterFixture(adapterInstance, adapter);
    }

    @Bean
    MockRepositorySourceFixture.MockRepositorySource mockRepositorySource() {
        return Mockito.mock(MockRepositorySourceFixture.MockRepositorySource.class);
    }

    @Bean
    MockRepositorySourceFixture.TestRepositorySourcePlugin testRepositorySourcePlugin(MockRepositorySourceFixture.MockRepositorySource instance) {
        var sourcePlugin = Mockito.mock(MockRepositorySourceFixture.TestRepositorySourcePlugin.class);
        Mockito.when(sourcePlugin.getSourceType()).thenReturn(new SourceType("test"));
        Mockito.when(sourcePlugin.instantiate(any())).thenReturn(instance);
        return sourcePlugin;
    }

    @Bean
    MockRepositorySourceFixture mockRepositorySourceFixture(MockRepositorySourceFixture.TestRepositorySourcePlugin plugin, MockRepositorySourceFixture.MockRepositorySource instance) {
        return new MockRepositorySourceFixture(instance, plugin);
    }
}
