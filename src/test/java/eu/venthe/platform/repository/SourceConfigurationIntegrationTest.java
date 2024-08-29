package eu.venthe.platform.repository;

import eu.venthe.platform.IntegrationTest;
import eu.venthe.platform.repository.application.SourceConfigurationAlreadyExistsException;
import eu.venthe.platform.repository.application.SourceConfigurationQueryService;
import eu.venthe.platform.repository.application.SourceConfigurationCommandService;
import eu.venthe.platform.repository.application.SourceConfigurationDto;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

class SourceConfigurationIntegrationTest extends IntegrationTest {
    @Autowired
    private SourceConfigurationCommandService commandService;
    @Autowired
    private SourceConfigurationQueryService queryService;

    @Test
    void sourceCanBeCreated() {
        // Given
        var name = randomSourceConfigurationName();

        // When
        commandService.register(name);
        var sourceInformation = queryService.getSourceInformation(name);

        // Then
        Assertions.assertThat(sourceInformation)
                .isPresent()
                .hasValue(new SourceConfigurationDto(name));
    }

    @Test
    void duplicateSourceCreationFails() {
        // Given
        var name = randomSourceConfigurationName();
        commandService.register(name);

        // When
        ThrowableAssert.ThrowingCallable action = () -> commandService.register(name);

        // Then
        Assertions.assertThatThrownBy(action)
                .isInstanceOf(SourceConfigurationAlreadyExistsException.class);
    }

    private static String randomSourceConfigurationName() {
        return "Test-Source-Configuration-%s".formatted(UUID.randomUUID().toString());
    }
}
