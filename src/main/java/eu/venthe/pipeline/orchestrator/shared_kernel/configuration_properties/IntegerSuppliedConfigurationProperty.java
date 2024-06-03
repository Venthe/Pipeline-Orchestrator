package eu.venthe.pipeline.orchestrator.shared_kernel.configuration_properties;

public record IntegerSuppliedConfigurationProperty(Integer value) implements SuppliedConfigurationProperty {
    @Override
    public boolean isInteger() {
        return true;
    }

    @Override
    public int asInteger() {
        return value;
    }
}
