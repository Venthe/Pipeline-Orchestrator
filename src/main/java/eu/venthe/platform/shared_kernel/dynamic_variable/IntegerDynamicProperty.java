package eu.venthe.platform.shared_kernel.dynamic_variable;

public record IntegerDynamicProperty(Integer value) implements DynamicProperty {
    @Override
    public boolean isInteger() {
        return true;
    }

    @Override
    public int asInteger() {
        return value;
    }
}
