package eu.venthe.platform.repository.domain;

import eu.venthe.platform.repository.domain.events.CreateRepositoryCommand;
import eu.venthe.platform.repository.domain.events.SourceRegisteredEvent;
import eu.venthe.platform.repository.domain.events.SynchronizeRepositoriesCommand;
import eu.venthe.platform.repository.plugin.template.Repository;
import eu.venthe.platform.repository.plugin.template.RepositorySourcePluginInstance;
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

        Assertions.assertThat(sourceConfiguration.data().getName()).isEqualTo("Example-Source");
        Assertions.assertThat(sourceConfiguration.messages()).containsExactlyInAnyOrder(new SourceRegisteredEvent("Example-Source"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = " ")
    void invalidSourceNameResultsInError(String value) {
        ThrowableAssert.ThrowingCallable action = () -> SourceConfiguration.create(value, Mockito.mock());

        Assertions.assertThatThrownBy(action)
                .isInstanceOf(InvalidSourceConfigurationNameException.class)
                .hasMessage("Source configuration name \"%s\" is not correct.".formatted(value));
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
    void shouldSendSynchronizationEventOnSynchronize() {
        // Given
        var sourceConfiguration = SourceConfiguration.create("Example-Source", Mockito.mock()).data();

        // When
        var messages = sourceConfiguration.synchronize();

        // Then
        Assertions.assertThat(messages)
                .containsExactly(new SynchronizeRepositoriesCommand("Example-Source"));
    }

    @Test
    void shouldSendCreateEventForEachRepository() {
        // Given
        var mockPluginInstance = Mockito.mock(RepositorySourcePluginInstance.class);
        var sourceConfiguration = SourceConfiguration.create("Example-Source", mockPluginInstance).data();
        Mockito.when(mockPluginInstance.getAllRepositories()).thenReturn(Set.of(
                new Repository("Repository-1"),
                new Repository("Repository-2")
        ));

        // When
        var messages = sourceConfiguration.synchronize();

        // Then
        Assertions.assertThat(messages)
                .containsExactlyInAnyOrder(
                        new SynchronizeRepositoriesCommand("Example-Source"),
                        new CreateRepositoryCommand("Repository-1", "Example-Source"),
                        new CreateRepositoryCommand("Repository-2", "Example-Source")
                );
    }
}
