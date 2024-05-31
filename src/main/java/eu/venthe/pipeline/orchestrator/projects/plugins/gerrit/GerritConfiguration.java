package eu.venthe.pipeline.orchestrator.projects.plugins.gerrit;

import eu.venthe.pipeline.orchestrator.projects.plugins.template.model.ConfigurationPropertyDefinition;
import eu.venthe.pipeline.orchestrator.projects.plugins.template.model.PropertyName;
import eu.venthe.pipeline.orchestrator.projects.plugins.template.model.SuppliedConfigurationProperty;
import eu.venthe.pipeline.orchestrator.projects.plugins.template.model.SuppliedProperties;
import lombok.Builder;
import lombok.Value;

import java.util.Map;

import static eu.venthe.pipeline.orchestrator.projects.plugins.template.model.ConfigurationPropertyDefinition.simpleText;

@Builder
@Value
public class GerritConfiguration {

    public static Map<PropertyName, ConfigurationPropertyDefinition> DEFINITIONS = Map.ofEntries(
            Map.entry(new PropertyName("basePath"), simpleText()),
            Map.entry(new PropertyName("password"), ConfigurationPropertyDefinition.builder().masked(true).asText().build()),
            Map.entry(new PropertyName("username"), simpleText())
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
