package eu.venthe.pipeline.orchestrator.projects.source_configuration.plugins.gerrit;

import eu.venthe.pipeline.orchestrator.shared_kernel.configuration_properties.ConfigurationPropertyDefinition;
import eu.venthe.pipeline.orchestrator.shared_kernel.configuration_properties.PropertyName;
import eu.venthe.pipeline.orchestrator.shared_kernel.configuration_properties.SuppliedConfigurationProperty;
import eu.venthe.pipeline.orchestrator.shared_kernel.configuration_properties.SuppliedProperties;
import lombok.Builder;
import lombok.Value;

import java.util.Map;

@Builder
@Value
public class GerritConfiguration {

    public static Map<PropertyName, ConfigurationPropertyDefinition> DEFINITIONS = Map.ofEntries(
            Map.entry(new PropertyName("basePath"), ConfigurationPropertyDefinition.simpleText()),
            Map.entry(new PropertyName("password"), ConfigurationPropertyDefinition.builder().masked(true).asText().build()),
            Map.entry(new PropertyName("username"), ConfigurationPropertyDefinition.simpleText())
    );

    String basePath;
    String password;
    String username;

    public static GerritConfiguration construct(SuppliedProperties properties) {
        return GerritConfiguration.builder()
                .basePath(properties.get("basePath").map(SuppliedConfigurationProperty::asText).orElse(null))
                .password(properties.get("password").map(SuppliedConfigurationProperty::asText).orElse(null))
                .username(properties.get("username").map(SuppliedConfigurationProperty::asText).orElse(null))
                .build();
    }
}
