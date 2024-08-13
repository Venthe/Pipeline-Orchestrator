package eu.venthe.platform.shared_kernel.configuration_properties;

import eu.venthe.platform.shared_kernel.dynamic_variable.BooleanDynamicProperty;
import eu.venthe.platform.shared_kernel.dynamic_variable.IntegerDynamicProperty;
import eu.venthe.platform.shared_kernel.dynamic_variable.DynamicProperty;
import eu.venthe.platform.shared_kernel.dynamic_variable.TextDynamicProperty;
import lombok.Builder;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Builder
public record SuppliedProperties(Map<PropertyName, DynamicProperty> properties) {
    public SuppliedProperties {
        if (properties == null) properties = new HashMap<>();
    }

    public static SuppliedProperties none() {
        return SuppliedProperties.builder().build();
    }

    public Optional<DynamicProperty> get(String key) {
        return Optional.ofNullable(properties.get(new PropertyName(key)));
    }

    public static class SuppliedPropertiesBuilder {
        private Map<PropertyName, DynamicProperty> properties = new HashMap<>();

        public SuppliedPropertiesBuilder property(String key, String value) {
            var entry = Map.entry(new PropertyName(key), new TextDynamicProperty(value));
            properties.put(entry.getKey(), entry.getValue());
            return this;
        }

        public SuppliedPropertiesBuilder property(String key, boolean value) {
            var entry = Map.entry(new PropertyName(key), new BooleanDynamicProperty(value));
            properties.put(entry.getKey(), entry.getValue());
            return this;
        }

        public SuppliedPropertiesBuilder property(String key, int value) {
            var entry = Map.entry(new PropertyName(key), new IntegerDynamicProperty(value));
            properties.put(entry.getKey(), entry.getValue());
            return this;
        }
    }
}
