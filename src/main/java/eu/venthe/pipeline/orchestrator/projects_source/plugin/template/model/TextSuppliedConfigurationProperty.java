package eu.venthe.pipeline.orchestrator.projects_source.plugin.template.model;

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
