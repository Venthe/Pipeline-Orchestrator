package eu.venthe.pipeline.orchestrator.modules.automation.runners.runner_engine.template.model;

import java.util.Locale;

public record RunnerEngineInstanceId(String value) {
    public RunnerEngineInstanceId(String value) {
        this.value = value.toLowerCase(Locale.ROOT);
    }
}
