package eu.venthe.platform.repository.domain;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class SourceConfigurationTest {
    @Test
    void source_can_be_created() {
        var sourceConfiguration = new SourceConfiguration("Example-Source");

        Assertions.assertThat(sourceConfiguration.getName()).isEqualTo("Example-Source");
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = " ")
    void invalid_source_name_results_in_error(String value) {
        ThrowableAssert.ThrowingCallable action = () -> new SourceConfiguration(value);

        Assertions.assertThatThrownBy(action)
                .isInstanceOf(InvalidSourceConfigurationNameException.class)
                .hasMessage("Source configuration name \"%s\" is not correct.".formatted(value));
    }

    @Test
    void two_sources_of_same_name_are_equal() {
        var exampleSourceConfigurationName = "Example-Source";
        var a1 = new SourceConfiguration(exampleSourceConfigurationName);
        var a2 = new SourceConfiguration(exampleSourceConfigurationName);

        Assertions.assertThat(a1).isEqualTo(a2);
    }

    @Test
    void two_sources_of_differing_name_are_not_equal() {
        var a = new SourceConfiguration("Example-Source-a");
        var b = new SourceConfiguration("Example-Source-b");

        Assertions.assertThat(a).isNotEqualTo(b);
    }
}
