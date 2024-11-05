package eu.venthe.platform.projects;

import eu.venthe.platform.projects.plugin.template.RepositorySourcePlugin;
import eu.venthe.platform.projects.plugin.template.RepositorySourcePluginInstance;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import static org.mockito.ArgumentMatchers.any;

@TestConfiguration
public class SourceConfigurationTestConfiguration {
    public static final String MOCK_SOURCE_PLUGIN_TYPE = "mock_source";

    @Bean
    MockRepositorySourcePluginInstance dummyPluginInstance() {
        var mock = Mockito.mock(MockRepositorySourcePluginInstance.class);
        Mockito.when(mock.getSourceType()).thenReturn(MOCK_SOURCE_PLUGIN_TYPE);
        return mock;
    }

    @Bean
    MockRepositorySourcePlugin dummyPlugin(MockRepositorySourcePluginInstance pluginInstance) {
        var mock = Mockito.mock(MockRepositorySourcePlugin.class);
        Mockito.when(mock.getSourceType()).thenReturn(MOCK_SOURCE_PLUGIN_TYPE);
        Mockito.when(mock.instantiate(any())).thenReturn(pluginInstance);
        return mock;
    }

    public interface MockRepositorySourcePluginInstance extends RepositorySourcePluginInstance {
    }

    public interface MockRepositorySourcePlugin extends RepositorySourcePlugin {
    }
}
