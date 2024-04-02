package eu.venthe.pipeline.orchestrator.plugins.projects;

import lombok.*;

import java.util.Map;
import java.util.Optional;

public interface ProjectPlugin {
    String getSourceType();

    ProjectProvider getProjectProvider(Map<String, String> properties);

    VersionControlSystemProvider getVersionControlSystem(Map<String, String> properties);

    Map<String, Property> getRequiredProperties();

    @Value
    @Builder
    class Property {
        @NonNull
        String value;

        @NonNull
        String type;

        @Getter(AccessLevel.NONE)
        @NonNull
        @Builder.Default
        Boolean required = false;

        @Getter(AccessLevel.NONE)
        String defaultValue;

        @Getter(AccessLevel.NONE)
        @NonNull
        @Builder.Default
        Boolean masked = false;

        public Boolean isRequired() {
            return required;
        }

        public Boolean isMasked() {
            return required;
        }

        public Optional<String> getDefaultValue() {
            return Optional.ofNullable(defaultValue);
        }
    }
}
