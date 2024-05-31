package eu.venthe.pipeline.orchestrator.projects.source_configuration.plugins.template.model;

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
