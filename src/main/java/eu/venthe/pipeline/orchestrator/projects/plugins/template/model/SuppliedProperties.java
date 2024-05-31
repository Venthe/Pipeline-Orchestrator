package eu.venthe.pipeline.orchestrator.projects.plugins.template.model;

import lombok.*;

import java.util.Map;
import java.util.Optional;

@Builder
@Value
@AllArgsConstructor
public class SuppliedProperties {
    @Singular
    Map<PropertyName, SuppliedConfigurationProperty> properties;

    public Optional<SuppliedConfigurationProperty> get(String key) {
        return Optional.ofNullable(properties.get(new PropertyName(key)));
    }
}
