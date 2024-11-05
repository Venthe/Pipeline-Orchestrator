package eu.venthe.platform.projects;

import eu.venthe.platform.IntegrationTest;
import eu.venthe.platform.projects.application.SourceConfigurationAlreadyExistsException;
import eu.venthe.platform.projects.application.SourceConfigurationCommandService;
import eu.venthe.platform.projects.application.SourceConfigurationDto;
import eu.venthe.platform.projects.application.SourceConfigurationQueryService;
import eu.venthe.platform.projects.plugin.template.Repository;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import java.util.Set;
import java.util.UUID;

import static eu.venthe.platform.projects.SourceConfigurationTestConfiguration.*;
import static eu.venthe.platform.projects.SourceConfigurationTestConfiguration.MOCK_SOURCE_PLUGIN_TYPE;

@Import(SourceConfigurationTestConfiguration.class)
class SourceConfigurationIntegrationTest extends IntegrationTest {
    @Autowired
    private SourceConfigurationCommandService commandService;
    @Autowired
    private SourceConfigurationQueryService queryService;
    @Autowired
    private MockRepositorySourcePluginInstance mockRepositorySourcePluginInstance;

    @Test
    void sourceCanBeCreated() {
        // Given
        var name = randomSourceConfigurationName();

        // When
        commandService.register(name, MOCK_SOURCE_PLUGIN_TYPE);
        var sourceInformation = queryService.getSourceInformation(name);

        // Then
        Assertions.assertThat(sourceInformation)
                .isPresent()
                .hasValue(new SourceConfigurationDto(name, MOCK_SOURCE_PLUGIN_TYPE));
    }

    @Test
    void duplicateSourceCreationFails() {
        // Given
        var name = randomSourceConfigurationName();
        commandService.register(name, MOCK_SOURCE_PLUGIN_TYPE);

        // When
        ThrowableAssert.ThrowingCallable action = () -> commandService.register(name, MOCK_SOURCE_PLUGIN_TYPE);

        // Then
        Assertions.assertThatThrownBy(action)
                .isInstanceOf(SourceConfigurationAlreadyExistsException.class);
    }

    @Test
    void sourceProvidesRepositories() {
        // Given
        var name = randomSourceConfigurationName();
        var dummyRepository = new Repository("Dummy-Repository");
        Mockito.when(mockRepositorySourcePluginInstance.getAllRepositories()).thenReturn(Set.of(dummyRepository));
        commandService.register(name, MOCK_SOURCE_PLUGIN_TYPE);

        // When
        var repositories = queryService.getAllRepositories(name);

        // Then
        Assertions.assertThat(repositories)
                .containsExactlyInAnyOrder(dummyRepository);
    }

    private static String randomSourceConfigurationName() {
        return "Test-Source-Configuration-%s".formatted(UUID.randomUUID().toString());
    }
}
