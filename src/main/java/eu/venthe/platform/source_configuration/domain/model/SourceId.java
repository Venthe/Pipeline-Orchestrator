package eu.venthe.platform.source_configuration.domain.model;

import java.util.UUID;

public record SourceId(String value) {
    public SourceId() {
        this(UUID.randomUUID().toString());
    }
}
