package eu.venthe.pipeline.orchestrator.shared_kernel.configuration_properties;

public record TextSuppliedConfigurationProperty(String value) implements SuppliedConfigurationProperty {
    @Override
    public boolean isText() {
        return true;
    }

    @Override
    public String asText() {
        return value;
    }
}
