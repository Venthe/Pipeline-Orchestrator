package eu.venthe.pipeline.orchestrator.projects.domain.plugin_template.model;

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