package eu.venthe.pipeline.orchestrator.shared_kernel.configuration_properties;

import lombok.Builder;
import lombok.Singular;

import java.util.Map;
import java.util.Optional;

@Builder
public record SuppliedProperties(@Singular Map<PropertyName, SuppliedConfigurationProperty> properties) {
    public static class SuppliedPropertiesBuilder {
        public SuppliedPropertiesBuilder property(String key, String value) {
            var entry = Map.entry(new PropertyName(key), new TextSuppliedConfigurationProperty(value));
            property(entry.getKey(), entry.getValue());
            return this;
        }

        public SuppliedPropertiesBuilder property(String key, boolean value) {
            var entry = Map.entry(new PropertyName(key), new BooleanSuppliedConfigurationProperty(value));
            property(entry.getKey(), entry.getValue());
            return this;
        }

        public SuppliedPropertiesBuilder property(String key, int value) {
            var entry = Map.entry(new PropertyName(key), new IntegerSuppliedConfigurationProperty(value));
            property(entry.getKey(), entry.getValue());
            return this;
        }
    }

    public static SuppliedProperties none() {
        return SuppliedProperties.builder().build();
    }

    public Optional<SuppliedConfigurationProperty> get(String key) {
        return Optional.ofNullable(properties.get(new PropertyName(key)));
    }
}
