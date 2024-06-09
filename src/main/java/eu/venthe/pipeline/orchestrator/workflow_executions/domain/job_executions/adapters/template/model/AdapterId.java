package eu.venthe.pipeline.orchestrator.workflow_executions.domain.job_executions.adapters.template.model;

import java.util.Locale;

public record AdapterId(String value) {
    public AdapterId(String value) {
        this.value = value.toLowerCase(Locale.ROOT);
    }
}
