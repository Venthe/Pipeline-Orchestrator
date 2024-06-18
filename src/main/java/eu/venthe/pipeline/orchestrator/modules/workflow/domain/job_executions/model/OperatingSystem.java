package eu.venthe.pipeline.orchestrator.modules.workflow.domain.job_executions.model;

import lombok.Getter;

@Getter
public enum OperatingSystem implements Dimension.Value {
    WINDOWS(Constants.OPERATING_SYSTEM_CONSTANTS, "windows"),
    LINUX(Constants.OPERATING_SYSTEM_CONSTANTS, "linux"),
    MACOS(Constants.OPERATING_SYSTEM_CONSTANTS, "macos");

    OperatingSystem(String key, String value) {
        this.value = new Dimension(key, value);
    }

    Dimension value;

    private static class Constants {
        private static final String OPERATING_SYSTEM_CONSTANTS = "operating_system";
    }
}
