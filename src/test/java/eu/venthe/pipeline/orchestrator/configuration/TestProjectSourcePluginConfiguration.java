package eu.venthe.pipeline.orchestrator.configuration;

import eu.venthe.pipeline.orchestrator.projects.domain.source_configurations.plugins.template.ProjectSourcePlugin;
import eu.venthe.pipeline.orchestrator.projects.domain.source_configurations.plugins.template.model.SourceType;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import static org.mockito.ArgumentMatchers.any;

@TestConfiguration
public class TestProjectSourcePluginConfiguration {
    @Bean
    public TestProjectSourcePluginPluginInstance testProjectSourcePluginPluginInstance() {
        return Mockito.mock(TestProjectSourcePluginPluginInstance.class);
    }

    @Bean
    public TestProjectSourcePlugin testProjectSourcePlugin(TestProjectSourcePluginPluginInstance instance) {
        var mock = Mockito.mock(TestProjectSourcePlugin.class);
        Mockito.when(mock.getSourceType()).thenReturn(new SourceType("test"));
        Mockito.when(mock.instantiate(any())).thenReturn(instance);
        return mock;
    }

    public interface TestProjectSourcePlugin extends ProjectSourcePlugin {}

    public interface TestProjectSourcePluginPluginInstance extends ProjectSourcePlugin.PluginInstance {}
}
