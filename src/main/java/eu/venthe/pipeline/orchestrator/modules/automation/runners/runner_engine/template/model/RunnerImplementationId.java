package eu.venthe.pipeline.orchestrator.modules.automation.runners.runner_engine.template.model;

import java.util.Locale;

public record RunnerImplementationId(String value) {
    public RunnerImplementationId(String value) {
        this.value = value.toLowerCase(Locale.ROOT);
    }
}
