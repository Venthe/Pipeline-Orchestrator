package eu.venthe.pipeline.runners.runner_engine.template.model.dimensions;

import lombok.Getter;

@Getter
public enum OperatingSystemDimension implements Dimension.Value {
    WINDOWS(Constants.OPERATING_SYSTEM_CONSTANTS, "windows"),
    LINUX(Constants.OPERATING_SYSTEM_CONSTANTS, "linux"),
    MACOS(Constants.OPERATING_SYSTEM_CONSTANTS, "macos");

    OperatingSystemDimension(String key, String value) {
        this.value = new Dimension(key, value);
    }

    Dimension value;

    private static class Constants {
        private static final String OPERATING_SYSTEM_CONSTANTS = "operating_system";
    }
}
