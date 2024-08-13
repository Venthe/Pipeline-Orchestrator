package eu.venthe.platform.runner.runner_engine.template.model;

import java.util.UUID;

public record RunCallbackToken(String value) {
    public RunCallbackToken() {
        this(UUID.randomUUID().toString());
    }
}
