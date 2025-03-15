package eu.venthe.platform.registries.domain;

import eu.venthe.platform.registries.domain.plugins.RegistryPluginConfiguration;
import eu.venthe.platform.registries.domain.plugins.RegistryPluginInstance;
import eu.venthe.platform.registries.domain.plugins.RegistryPluginProvider;
import eu.venthe.platform.registries.domain.plugins.RegistryType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RegistryTest {

    private static final RegistryType VALID_REGISTRY_TYPE = new RegistryType("mock");
    private static final RegistryPluginConfiguration VALID_PLUGIN_CONFIGURATION = new RegistryPluginConfiguration(VALID_REGISTRY_TYPE);
    private static final RepositoryCorrelationIdentifier VALID_REPOSITORY_CORRELATION_IDENTIFIER = new RepositoryCorrelationIdentifier("mock");

    RegistryPluginProvider pluginProvider = mock(RegistryPluginProvider.class);
    RegistryFactory registryFactory = new RegistryFactory(pluginProvider);
    RegistryPluginInstance pluginInstance = mock(RegistryPluginInstance.class);

    @Test
    void registryCanBeCreated() {
        // given
        when(pluginProvider.provide(VALID_PLUGIN_CONFIGURATION))
                .thenReturn(pluginInstance);
        when(pluginInstance.getType()).thenReturn(VALID_REGISTRY_TYPE);

        // when
        var registry = registryFactory.create(VALID_PLUGIN_CONFIGURATION);

        // then
        assertThat(registry).isNotNull();
        assertThat(registry.getIdentifier().value())
                .isNotNull()
                .isNotBlank();
        assertThat(registry.getType())
                .isEqualTo(VALID_PLUGIN_CONFIGURATION.registryType());
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void healthcheckWorks(Boolean healthy) {
        // given
        when(pluginProvider.provide(VALID_PLUGIN_CONFIGURATION))
                .thenReturn(pluginInstance);
        when(pluginInstance.checkHealth()).thenReturn(healthy);

        // when
        var registry = registryFactory.create(VALID_PLUGIN_CONFIGURATION);

        // then
        assertThat(registry.checkHealth()).isEqualTo(healthy);
    }

    @Test
    void requestedRepositoriesAreEnrichedWithRegistryIdentifier() {
        // given
        when(pluginProvider.provide(VALID_PLUGIN_CONFIGURATION))
                .thenReturn(pluginInstance);
        when(pluginInstance.getRepositories()).thenReturn(List.of(UnmanagedRepository.builder().repositoryCorrelationIdentifier(VALID_REPOSITORY_CORRELATION_IDENTIFIER)));

        // when
        var registry = registryFactory.create(VALID_PLUGIN_CONFIGURATION);

        // then
        assertThat(registry.getRepositories())
                .containsExactlyInAnyOrder(
                        new UnmanagedRepository(VALID_REPOSITORY_CORRELATION_IDENTIFIER, registry.getIdentifier())
                );
    }

}
