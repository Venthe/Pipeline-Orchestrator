package eu.venthe.pipeline.orchestrator.projects_source.adapter.template.model;

import java.util.Optional;

public interface ConfigurationPropertyDefinition {
    default Boolean isRequired() {
        return false;
    }

    default Boolean isMasked() {
        return false;
    }

    default Optional<ConfigurationProperty> getDefaultValue() {
        return Optional.empty();
    }
}
