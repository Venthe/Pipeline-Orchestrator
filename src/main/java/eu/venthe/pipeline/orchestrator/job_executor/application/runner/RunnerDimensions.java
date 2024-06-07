package eu.venthe.pipeline.orchestrator.job_executor.application.runner;

import lombok.*;

import java.util.HashMap;
import java.util.Map;

@Value
public class RunnerDimensions {
    Map<String, String> dimensions = new HashMap<>();

    public String put(String key, String value) {
        return dimensions.put(key, value);
    }

    @RequiredArgsConstructor
    @ToString
    @EqualsAndHashCode
    @Getter
    public static class Dimension implements Map.Entry<String, String> {
        private final String key;
        private final String value;

        @Override
        public String setValue(String value) {
            throw new UnsupportedOperationException();
        }
    }

    @Getter
    public enum OperatingSystem {
        WINDOWS("operating_system", "windows"),
        LINUX("operating_system", "linux"),
        MACOS("operating_system", "macos");

        OperatingSystem(String key, String value) {
            this.value = new Dimension(key, value);
        }

        Dimension value;
    }

    @Getter
    public enum Architecture {
        X64("architecture", "x64"),
        ARM64("architecture", "arm64"),
        ARM32("architecture", "arm32");

        Architecture(String key, String value) {
            this.value = new Dimension(key, value);
        }

        Dimension value;
    }

    @Value
    @EqualsAndHashCode(callSuper = true)
    @ToString(callSuper = true)
    public static class ContainerImage extends Dimension {
        public ContainerImage(String value) {
            super("container_image", value);
        }
    }
}
