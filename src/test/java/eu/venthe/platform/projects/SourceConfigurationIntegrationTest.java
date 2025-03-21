package eu.venthe.platform.projects;

import eu.venthe.platform.IntegrationTest;
import eu.venthe.platform.projects.application.*;
import eu.venthe.platform.projects.domain.ManagedRepository;
import eu.venthe.platform.projects.domain.ProjectCorrelationId;
import eu.venthe.platform.projects.plugin.template.Repository;
import org.assertj.core.api.Assertions;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import java.util.Optional;
import java.util.Set;

import static eu.venthe.platform.projects.SourceConfigurationTestConfiguration.MOCK_SOURCE_PLUGIN_TYPE;
import static eu.venthe.platform.projects.SourceConfigurationTestConfiguration.MockRepositorySourcePluginInstance;

@Import({SourceConfigurationTestConfiguration.class, TestTimeConfiguration.class})
class SourceConfigurationIntegrationTest extends IntegrationTest {
    private static final Repository EXAMPLE_REPOSITORY = new Repository(new ProjectCorrelationId("Dummy-Repository"), "main", "1234");

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
        // When
        var sourceIdentifier = sourceConfigurationCommandService.register(MOCK_SOURCE_PLUGIN_TYPE);
        var sourceInformation = sourceConfigurationQueryService.getSourceInformation(sourceIdentifier);

        // Then
        Assertions.assertThat(sourceInformation)
                .isPresent()
                .hasValue(new SourceConfigurationDto(sourceIdentifier, MOCK_SOURCE_PLUGIN_TYPE));
    }

    @Test
    void sourceProvidesRepositories() {
        // Given
        var dummyRepository = EXAMPLE_REPOSITORY;
        Mockito.when(mockRepositorySourcePluginInstance.getAllRepositories()).thenReturn(Set.of(dummyRepository));
        var sourceIdentifier = sourceConfigurationCommandService.register(MOCK_SOURCE_PLUGIN_TYPE);

        // When
        var repositories = sourceConfigurationQueryService.getAllRepositories(sourceIdentifier);

        // Then
        Assertions.assertThat(repositories)
                .containsExactlyInAnyOrder(toManagedRepository(dummyRepository));
    }

    @Test
    void sourceResolvesRepositories() {
        // Given
        var dummyRepository = EXAMPLE_REPOSITORY;
        Mockito.when(mockRepositorySourcePluginInstance.getRepository(new ProjectCorrelationId("Dummy-Repository"))).thenReturn(Optional.of(dummyRepository));
        var sourceIdentifier = sourceConfigurationCommandService.register(MOCK_SOURCE_PLUGIN_TYPE);

        // When
        var repository = sourceConfigurationQueryService.getRepository(sourceIdentifier, dummyRepository.correlationId());

        // Then
        Assertions.assertThat(repository)
                .isPresent()
                .hasValue(toManagedRepository(dummyRepository));
    }

    @Test
    void synchronizationCreatesRepositories() {
        // Given
        var dummyRepository = EXAMPLE_REPOSITORY;
        Mockito.when(mockRepositorySourcePluginInstance.getAllRepositories()).thenReturn(Set.of(dummyRepository));
        Mockito.when(mockRepositorySourcePluginInstance.getRepository(dummyRepository.correlationId())).thenReturn(Optional.of(dummyRepository));
        var sourceIdentifier = sourceConfigurationCommandService.register(MOCK_SOURCE_PLUGIN_TYPE);

        // When
        sourceConfigurationCommandService.synchronizeAll(sourceIdentifier);

        // Then
        Awaitility.await().untilAsserted(() ->
                Assertions.assertThat(projectQueryService.getProject(sourceIdentifier, dummyRepository.correlationId())).isPresent()
                        .hasValue(new ProjectDto(sourceIdentifier, dummyRepository.correlationId(), TestTimeConfiguration.NOW))
        );
    }

    private static ManagedRepository toManagedRepository(Repository repository) {
        return new ManagedRepository(repository.correlationId(), repository.trackedBranch(), repository.trackedBranchHash());
    }
}
