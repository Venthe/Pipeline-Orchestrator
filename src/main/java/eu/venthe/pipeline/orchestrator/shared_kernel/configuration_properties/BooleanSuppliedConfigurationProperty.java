package eu.venthe.pipeline.orchestrator.shared_kernel.configuration_properties;

public record BooleanSuppliedConfigurationProperty(Boolean value) implements SuppliedConfigurationProperty {
    @Override
    public boolean isBoolean() {
        return true;
    }

    @Override
    public boolean asBoolean() {
        return value;
    }
}
