package eu.venthe.platform.shared_kernel.dynamic_value;

public record StringDynamicValue(String value) implements DynamicValue {
    @Override
    public boolean isString() {
        return true;
    }

    @Override
    public String asString() {
        return value;
    }
}
