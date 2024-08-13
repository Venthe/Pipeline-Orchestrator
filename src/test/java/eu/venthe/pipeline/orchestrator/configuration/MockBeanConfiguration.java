package eu.venthe.pipeline.orchestrator.configuration;

import eu.venthe.pipeline.orchestrator.fixtures.MockAdapterFixture;
import eu.venthe.pipeline.orchestrator.fixtures.MockProjectSourceFixture;
import eu.venthe.pipeline.runners.runner_engine.template.model.RunnerEngineType;
import eu.venthe.pipeline.projects.domain.source_configurations.plugins.template.model.SourceType;
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
    MockProjectSourceFixture.MockProjectSource mockProjectSource() {
        return Mockito.mock(MockProjectSourceFixture.MockProjectSource.class);
    }

    @Bean
    MockProjectSourceFixture.TestProjectSourcePlugin testProjectSourcePlugin(MockProjectSourceFixture.MockProjectSource instance) {
        var sourcePlugin = Mockito.mock(MockProjectSourceFixture.TestProjectSourcePlugin.class);
        Mockito.when(sourcePlugin.getSourceType()).thenReturn(new SourceType("test"));
        Mockito.when(sourcePlugin.instantiate(any())).thenReturn(instance);
        return sourcePlugin;
    }

    @Bean
    MockProjectSourceFixture mockProjectSourceFixture(MockProjectSourceFixture.TestProjectSourcePlugin plugin, MockProjectSourceFixture.MockProjectSource instance) {
        return new MockProjectSourceFixture(instance, plugin);
    }
}
