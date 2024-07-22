package eu.venthe.pipeline.orchestrator.modules.automation.runners.adapters.template.model;

import java.util.Locale;

public record AdapterId(String value) {
    public AdapterId(String value) {
        this.value = value.toLowerCase(Locale.ROOT);
    }
}
