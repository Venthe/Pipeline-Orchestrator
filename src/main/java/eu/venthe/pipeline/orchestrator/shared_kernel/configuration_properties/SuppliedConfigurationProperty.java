package eu.venthe.pipeline.orchestrator.shared_kernel.configuration_properties;

public interface SuppliedConfigurationProperty {
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
