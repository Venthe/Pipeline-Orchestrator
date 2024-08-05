package eu.venthe.pipeline.orchestrator.modules.automation.runners.runner_engine.template.model;

import java.util.UUID;

public record ExecutionCallbackToken(String value) {
    public ExecutionCallbackToken() {
        this(UUID.randomUUID().toString());
    }
}
