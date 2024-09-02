package eu.venthe.platform.repository.domain;

import eu.venthe.platform.repository.domain.events.SourceRegisteredEvent;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class SourceConfigurationTest {
    @Test
    void sourceCanBeCreated() {
        var sourceConfiguration = SourceConfiguration.create("Example-Source");

        Assertions.assertThat(sourceConfiguration.data().getName()).isEqualTo("Example-Source");
        Assertions.assertThat(sourceConfiguration.messages()).containsExactlyInAnyOrder(new SourceRegisteredEvent("Example-Source"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = " ")
    void invalidSourceNameResultsInError(String value) {
        ThrowableAssert.ThrowingCallable action = () -> SourceConfiguration.create(value);

        Assertions.assertThatThrownBy(action)
                .isInstanceOf(InvalidSourceConfigurationNameException.class)
                .hasMessage("Source configuration name \"%s\" is not correct.".formatted(value));
    }

    @Test
    void twoSourcesOfSameNameAreEqual() {
        var exampleSourceConfigurationName = "Example-Source";
        var a1 = SourceConfiguration.create(exampleSourceConfigurationName).data();
        var a2 = SourceConfiguration.create(exampleSourceConfigurationName).data();

        Assertions.assertThat(a1).isEqualTo(a2);
    }

    @Test
    void twoSourcesOfDifferingNameAreNotEqual() {
        var a = SourceConfiguration.create("Example-Source-a").data();
        var b = SourceConfiguration.create("Example-Source-b").data();

        Assertions.assertThat(a).isNotEqualTo(b);
    }
}
