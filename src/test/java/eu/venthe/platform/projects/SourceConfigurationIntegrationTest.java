package eu.venthe.platform.projects;

import eu.venthe.platform.IntegrationTest;
import eu.venthe.platform.projects.application.*;
import eu.venthe.platform.projects.plugin.template.Repository;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static eu.venthe.platform.projects.SourceConfigurationTestConfiguration.MOCK_SOURCE_PLUGIN_TYPE;
import static eu.venthe.platform.projects.SourceConfigurationTestConfiguration.MockRepositorySourcePluginInstance;

@Import(SourceConfigurationTestConfiguration.class)
class SourceConfigurationIntegrationTest extends IntegrationTest {
    @Autowired
    private SourceConfigurationCommandService sourceConfigurationCommandService;
    @Autowired
    private SourceConfigurationQueryService sourceConfigurationQueryService;
    @Autowired
    private MockRepositorySourcePluginInstance mockRepositorySourcePluginInstance;
    @Autowired
    private ProjectQueryService projectQueryService;

    @Test
    void sourceCanBeCreated() {
        // Given
        var name = randomSourceConfigurationName();

        // When
        sourceConfigurationCommandService.register(name, MOCK_SOURCE_PLUGIN_TYPE);
        var sourceInformation = sourceConfigurationQueryService.getSourceInformation(name);

        // Then
        Assertions.assertThat(sourceInformation)
                .isPresent()
                .hasValue(new SourceConfigurationDto(name, MOCK_SOURCE_PLUGIN_TYPE));
    }

    @Test
    void duplicateSourceCreationFails() {
        // Given
        var name = randomSourceConfigurationName();
        sourceConfigurationCommandService.register(name, MOCK_SOURCE_PLUGIN_TYPE);

        // When
        ThrowableAssert.ThrowingCallable action = () -> sourceConfigurationCommandService.register(name, MOCK_SOURCE_PLUGIN_TYPE);

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
        sourceConfigurationCommandService.register(name, MOCK_SOURCE_PLUGIN_TYPE);

        // When
        var repositories = sourceConfigurationQueryService.getAllRepositories(name);

        // Then
        Assertions.assertThat(repositories)
                .containsExactlyInAnyOrder(dummyRepository);
    }

    @Test
    void sourceResolvesRepositories() {
        // Given
        var name = randomSourceConfigurationName();
        var dummyRepository = new Repository("Dummy-Repository");
        Mockito.when(mockRepositorySourcePluginInstance.getRepository("Dummy-Repository")).thenReturn(Optional.of(dummyRepository));
        sourceConfigurationCommandService.register(name, MOCK_SOURCE_PLUGIN_TYPE);

        // When
        var repository = sourceConfigurationQueryService.getRepository(name, dummyRepository.repositoryName());

        // Then
        Assertions.assertThat(repository)
                .isPresent()
                .hasValue(dummyRepository);
    }

    @Test
    void synchronizationCreatesRepositories() {
        // Given
        var name = randomSourceConfigurationName();
        var dummyRepository = new Repository("Dummy-Repository");
        Mockito.when(mockRepositorySourcePluginInstance.getAllRepositories()).thenReturn(Set.of(dummyRepository));
        Mockito.when(mockRepositorySourcePluginInstance.getRepository(dummyRepository.repositoryName())).thenReturn(Optional.of(dummyRepository));
        sourceConfigurationCommandService.register(name, MOCK_SOURCE_PLUGIN_TYPE);

        // When
        sourceConfigurationCommandService.synchronizeAll(name);

        // Then
        Awaitility.await().untilAsserted(() ->
                Assertions.assertThat(projectQueryService.getProject(name, dummyRepository.repositoryName())).isPresent().hasValue(new ProjectDto(name, dummyRepository.repositoryName()))
        );
    }

    private static String randomSourceConfigurationName() {
        return "Test-Source-Configuration-%s".formatted(UUID.randomUUID().toString());
    }
}
