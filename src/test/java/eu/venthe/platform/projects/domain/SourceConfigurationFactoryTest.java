package eu.venthe.platform.projects.domain;

import eu.venthe.platform.projects.domain.events.SourceRegisteredEvent;
import eu.venthe.platform.shared_kernel.dynamic_value.DynamicValue;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.Map;

class SourceConfigurationFactoryTest {
    private static final Map<String, DynamicValue> NO_PROPERTIES = Collections.emptyMap();
    private static final String EXAMPLE_SOURCE_TYPE = "Example";

    SourceConfigurationFactory factory = new SourceConfigurationFactory(Mockito.mock());

    @Test
    void sourceCanBeCreated() {
        var sourceConfiguration = factory.create(EXAMPLE_SOURCE_TYPE, NO_PROPERTIES);

        Assertions.assertThat(sourceConfiguration.data().getInternalIdentifier()).isNotNull();
        Assertions.assertThat(sourceConfiguration.messages()).containsExactlyInAnyOrder(new SourceRegisteredEvent(sourceConfiguration.data().getInternalIdentifier()));
    }
}
