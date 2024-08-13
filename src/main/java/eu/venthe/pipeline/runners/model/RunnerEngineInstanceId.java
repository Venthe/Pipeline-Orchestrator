package eu.venthe.pipeline.runners.model;

import java.util.Locale;

public record RunnerEngineInstanceId(String value) {
    public RunnerEngineInstanceId(String value) {
        this.value = value.toLowerCase(Locale.ROOT);
    }
}
