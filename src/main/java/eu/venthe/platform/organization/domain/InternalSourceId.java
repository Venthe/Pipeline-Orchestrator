package eu.venthe.platform.organization.domain;

import java.util.UUID;

public record InternalSourceId(String value) {
    public InternalSourceId() {
        this(UUID.randomUUID().toString());
    }
}
