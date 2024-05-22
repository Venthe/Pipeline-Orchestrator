package eu.venthe.pipeline.orchestrator.projects.plugin.template.model;

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
