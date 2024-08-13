package eu.venthe.pipeline.runners.runner_engine.template.model;

import java.util.UUID;

public record RunCallbackToken(String value) {
    public RunCallbackToken() {
        this(UUID.randomUUID().toString());
    }
}
