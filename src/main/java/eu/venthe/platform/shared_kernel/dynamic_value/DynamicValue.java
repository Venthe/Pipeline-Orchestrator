package eu.venthe.platform.shared_kernel.dynamic_value;

public interface DynamicValue {
    default boolean isString() {
        return false;
    }

    default boolean isBoolean() {
        return false;
    }

    default boolean isInteger() {
        return false;
    }

    default String asString() {
        throw new IllegalArgumentException();
    }

    default boolean asBoolean() {
        throw new IllegalArgumentException();
    }

    default int asInteger() {
        throw new IllegalArgumentException();
    }
}
