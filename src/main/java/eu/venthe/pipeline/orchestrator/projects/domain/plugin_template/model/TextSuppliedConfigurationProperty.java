package eu.venthe.pipeline.orchestrator.projects.domain.plugin_template.model;

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
