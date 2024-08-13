package eu.venthe.platform.shared_kernel.configuration_properties;

import eu.venthe.platform.shared_kernel.dynamic_variable.DynamicProperty;
import lombok.Builder;
import lombok.Value;

import java.util.Optional;

@Builder
@Value
public class ConfigurationPropertyDefinition {
    @Builder.Default
    Boolean required = false;
    @Builder.Default
    Boolean masked = false;

    Optional<DynamicProperty> getDefaultValue() {
        return Optional.empty();
    }

    ConfigurationPropertyType type;

    public static ConfigurationPropertyDefinition simpleText() {
        return builder().asText().build();
    }

    public static ConfigurationPropertyDefinition simpleInteger() {
        return builder().asInteger().build();
    }

    public static ConfigurationPropertyDefinition simpleBoolean() {
        return builder().asBoolean().build();
    }

    public static class ConfigurationPropertyDefinitionBuilder {

        public ConfigurationPropertyDefinitionBuilder asText() {
            this.type = ConfigurationPropertyType.TEXT;
            return this;
        }

        public ConfigurationPropertyDefinitionBuilder asBoolean() {
            this.type = ConfigurationPropertyType.BOOLEAN;
            return this;
        }

        public ConfigurationPropertyDefinitionBuilder asInteger() {
            this.type = ConfigurationPropertyType.INTEGER;
            return this;
        }
    }
}
