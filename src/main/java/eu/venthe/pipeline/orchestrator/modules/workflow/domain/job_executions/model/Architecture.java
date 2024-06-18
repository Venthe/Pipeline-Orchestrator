package eu.venthe.pipeline.orchestrator.modules.workflow.domain.job_executions.model;

import lombok.Getter;

@Getter
public enum Architecture implements Dimension.Value {
    X64(Constants.ARCHITECTURE_KEY, "x64"),
    ARM64(Constants.ARCHITECTURE_KEY, "arm64"),
    ARM32(Constants.ARCHITECTURE_KEY, "arm32");

    Architecture(String key, String value) {
        this.value = new Dimension(key, value);
    }

    Dimension value;

    private static class Constants {
        private static final String ARCHITECTURE_KEY = "architecture";
    }
}
