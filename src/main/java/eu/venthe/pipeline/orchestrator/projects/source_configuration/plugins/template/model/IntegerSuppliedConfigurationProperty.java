package eu.venthe.pipeline.orchestrator.projects.source_configuration.plugins.template.model;

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
