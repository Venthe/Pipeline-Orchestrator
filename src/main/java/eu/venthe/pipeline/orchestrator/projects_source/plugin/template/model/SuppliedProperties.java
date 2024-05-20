package eu.venthe.pipeline.orchestrator.projects_source.plugin.template.model;

import lombok.Value;

import java.util.Map;
import java.util.Optional;

@Value
public class SuppliedProperties {
    Map<PropertyName, SuppliedConfigurationProperty> properties;

    public Optional<SuppliedConfigurationProperty> get(String key) {
        return Optional.ofNullable(properties.get(new PropertyName(key)));
    }
}
