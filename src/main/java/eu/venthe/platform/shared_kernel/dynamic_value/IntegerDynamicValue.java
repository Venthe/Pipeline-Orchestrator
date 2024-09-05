package eu.venthe.platform.shared_kernel.dynamic_value;

public record IntegerDynamicValue(int value) implements DynamicValue {
    @Override
    public boolean isInteger() {
        return true;
    }

    @Override
    public int asInteger() {
        return value;
    }
}
