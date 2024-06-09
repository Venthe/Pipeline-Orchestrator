package eu.venthe.pipeline.orchestrator.shared_kernel.configuration_properties;

import lombok.Builder;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Builder
public record SuppliedProperties(Map<PropertyName, SuppliedConfigurationProperty> properties) {
    public SuppliedProperties {
        if (properties == null) properties = new HashMap<>();
    }

    public static SuppliedProperties none() {
        return SuppliedProperties.builder().build();
    }

    public Optional<SuppliedConfigurationProperty> get(String key) {
        return Optional.ofNullable(properties.get(new PropertyName(key)));
    }

    public static class SuppliedPropertiesBuilder {
        private Map<PropertyName, SuppliedConfigurationProperty> properties = new HashMap<>();

        public SuppliedPropertiesBuilder property(String key, String value) {
            var entry = Map.entry(new PropertyName(key), new TextSuppliedConfigurationProperty(value));
            properties.put(entry.getKey(), entry.getValue());
            return this;
        }

        public SuppliedPropertiesBuilder property(String key, boolean value) {
            var entry = Map.entry(new PropertyName(key), new BooleanSuppliedConfigurationProperty(value));
            properties.put(entry.getKey(), entry.getValue());
            return this;
        }

        public SuppliedPropertiesBuilder property(String key, int value) {
            var entry = Map.entry(new PropertyName(key), new IntegerSuppliedConfigurationProperty(value));
            properties.put(entry.getKey(), entry.getValue());
            return this;
        }
    }
}
