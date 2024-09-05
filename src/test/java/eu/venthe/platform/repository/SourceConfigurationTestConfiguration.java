package eu.venthe.platform.repository;

import eu.venthe.platform.repository.plugin.template.RepositorySourcePlugin;
import eu.venthe.platform.repository.plugin.template.RepositorySourcePluginInstance;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.mockito.ArgumentMatchers.any;

@Configuration
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
