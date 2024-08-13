package eu.venthe.platform.shared_kernel.dynamic_variable;

public record BooleanDynamicProperty(Boolean value) implements DynamicProperty {
    @Override
    public boolean isBoolean() {
        return true;
    }

    @Override
    public boolean asBoolean() {
        return value;
    }
}
