package eu.venthe.pipeline.orchestrator.shared_kernel.dynamic_variable;

public interface DynamicProperty {
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
