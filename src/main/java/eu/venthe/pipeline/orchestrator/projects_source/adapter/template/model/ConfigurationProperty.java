package eu.venthe.pipeline.orchestrator.projects_source.adapter.template.model;

public interface ConfigurationProperty {
    default boolean isText() {
        return false;
    }

    default boolean isBoolean() {
        return false;
    }

    default boolean isInteger() {
        return false;
    }

    default String asText() {
        throw new IllegalArgumentException();
    }

    default boolean asBoolean() {
        throw new IllegalArgumentException();
    }

    default int asInteger() {
        throw new IllegalArgumentException();
    }
}
