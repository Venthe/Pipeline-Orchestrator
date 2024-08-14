package eu.venthe.platform.source_configuration.domain.model;

import java.util.UUID;

public record ConfigurationSourceId(String value) {
    public ConfigurationSourceId() {
        this(UUID.randomUUID().toString());
    }
}
