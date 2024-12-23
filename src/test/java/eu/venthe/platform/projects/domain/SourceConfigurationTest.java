package eu.venthe.platform.projects.domain;

import eu.venthe.platform.projects.domain.events.RegisterProjectCommand;
import eu.venthe.platform.projects.domain.events.SourceRegisteredEvent;
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
    @Test
    void sourceCanBeCreated() {
        var sourceConfiguration = SourceConfiguration.create("Example-Source", Mockito.mock());

        Assertions.assertThat(sourceConfiguration.data().getIdentifier()).isEqualTo("Example-Source");
        Assertions.assertThat(sourceConfiguration.messages()).containsExactlyInAnyOrder(new SourceRegisteredEvent("Example-Source"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = " ")
    void invalidSourceNameResultsInError(String value) {
        ThrowableAssert.ThrowingCallable action = () -> SourceConfiguration.create(value, Mockito.mock());

        Assertions.assertThatThrownBy(action)
                .isInstanceOf(InvalidSourceConfigurationIdentifierException.class)
                .hasMessage("Source configuration identifier \"%s\" is not correct.".formatted(value));
    }

    @Test
    void twoSourcesOfSameNameAreEqual() {
        var exampleSourceConfigurationName = "Example-Source";
        var a1 = SourceConfiguration.create(exampleSourceConfigurationName, Mockito.mock()).data();
        var a2 = SourceConfiguration.create(exampleSourceConfigurationName, Mockito.mock()).data();

        Assertions.assertThat(a1).isEqualTo(a2);
    }

    @Test
    void twoSourcesOfDifferingNameAreNotEqual() {
        var a = SourceConfiguration.create("Example-Source-a", Mockito.mock()).data();
        var b = SourceConfiguration.create("Example-Source-b", Mockito.mock()).data();

        Assertions.assertThat(a).isNotEqualTo(b);
    }

    @Test
    void shouldSendSynchronizationEventOnSynchronizeAll() {
        // Given
        var sourceConfiguration = SourceConfiguration.create("Example-Source", Mockito.mock()).data();

        // When
        var messages = sourceConfiguration.synchronizeAll();

        // Then
        Assertions.assertThat(messages)
                .containsExactly(new SynchronizeProjectsCommand("Example-Source"));
    }

    @Test
    void shouldSendCreateEventForEachRepository() {
        // Given
        var mockPluginInstance = Mockito.mock(RepositorySourcePluginInstance.class);
        var sourceConfiguration = SourceConfiguration.create("Example-Source", mockPluginInstance).data();
        Mockito.when(mockPluginInstance.getAllRepositories()).thenReturn(Set.of(
                new Repository("Repository-1", "main", "123"),
                new Repository("Repository-2", "master", "456")
        ));

        // When
        var messages = sourceConfiguration.synchronizeAll();

        // Then
        Assertions.assertThat(messages)
                .containsExactlyInAnyOrder(
                        new SynchronizeProjectsCommand("Example-Source"),
                        new RegisterProjectCommand("Example-Source", "Repository-1"),
                        new RegisterProjectCommand("Example-Source", "Repository-2")
                );
    }
}
