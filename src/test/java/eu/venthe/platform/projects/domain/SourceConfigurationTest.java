package eu.venthe.platform.projects.domain;

import eu.venthe.platform.projects.domain.events.RegisterProjectCommand;
import eu.venthe.platform.projects.domain.events.SynchronizeProjectsCommand;
import eu.venthe.platform.projects.plugin.template.Repository;
import eu.venthe.platform.projects.plugin.template.RepositorySourcePluginInstance;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;

import java.util.Set;

class SourceConfigurationTest {

    private static final SourceConfigurationInternalIdentifier EXAMPLE_SOURCE_IDENTIFIER = new SourceConfigurationInternalIdentifier("Example-Source");
    private final RepositorySourcePluginInstance mockRepositoryPluginInstance = Mockito.mock();

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = " ")
    void invalidSourceIdentifierResultsInError(String value) {
        ThrowableAssert.ThrowingCallable action = () -> new SourceConfiguration(new SourceConfigurationInternalIdentifier(value), mockRepositoryPluginInstance);

        Assertions.assertThatThrownBy(action)
                .isInstanceOf(InvalidSourceConfigurationIdentifierException.class)
                .hasMessage("Source configuration sourceConfigurationInternalIdentifier \"%s\" is not correct.".formatted(value));
    }

    @Test
    void twoSourcesOfSameIdentifierAreEqual() {
        var a1 = new SourceConfiguration(EXAMPLE_SOURCE_IDENTIFIER, mockRepositoryPluginInstance);
        var a2 = new SourceConfiguration(EXAMPLE_SOURCE_IDENTIFIER, mockRepositoryPluginInstance);

        Assertions.assertThat(a1).isEqualTo(a2);
    }

    @Test
    void twoSourcesOfDifferingIdentifierAreNotEqual() {
        var a = new SourceConfiguration(new SourceConfigurationInternalIdentifier("Example-Source-a"), mockRepositoryPluginInstance);
        var b = new SourceConfiguration(new SourceConfigurationInternalIdentifier("Example-Source-b"), mockRepositoryPluginInstance);

        Assertions.assertThat(a).isNotEqualTo(b);
    }

    @Test
    void shouldSendSynchronizationEventOnSynchronizeAll() {
        // Given
        var sourceConfiguration = new SourceConfiguration(EXAMPLE_SOURCE_IDENTIFIER, mockRepositoryPluginInstance);

        // When
        var messages = sourceConfiguration.synchronizeAll();

        // Then
        Assertions.assertThat(messages)
                .containsExactly(new SynchronizeProjectsCommand(EXAMPLE_SOURCE_IDENTIFIER));
    }

    @Test
    void shouldSendCreateEventForEachRepository() {
        // Given
        var sourceConfiguration = new SourceConfiguration(EXAMPLE_SOURCE_IDENTIFIER, mockRepositoryPluginInstance);
        Mockito.when(mockRepositoryPluginInstance.getAllRepositories()).thenReturn(Set.of(
                new Repository("Repository-1", "main", "123"),
                new Repository("Repository-2", "master", "456")
        ));

        // When
        var messages = sourceConfiguration.synchronizeAll();

        // Then
        Assertions.assertThat(messages)
                .containsExactlyInAnyOrder(
                        new SynchronizeProjectsCommand(EXAMPLE_SOURCE_IDENTIFIER),
                        new RegisterProjectCommand(EXAMPLE_SOURCE_IDENTIFIER, "Repository-1"),
                        new RegisterProjectCommand(EXAMPLE_SOURCE_IDENTIFIER, "Repository-2")
                );
    }
}
