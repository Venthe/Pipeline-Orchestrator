package eu.venthe.platform.registries.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RegistryTest {

    RegistryFactory registryFactory = new RegistryFactory();

    @Test
    void registryCanBeCreated() {
        var registry = registryFactory.create();

        assertThat(registry).isNotNull();
        assertThat(registry.getIdentifier().value())
                .isNotNull()
                .isNotBlank();
    }

}
