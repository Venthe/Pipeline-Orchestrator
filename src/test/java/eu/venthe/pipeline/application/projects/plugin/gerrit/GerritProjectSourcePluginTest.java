package eu.venthe.pipeline.application.projects.plugin.gerrit;

import eu.venthe.pipeline.project.domain.source_configurations.plugins.gerrit.GerritProjectSourcePlugin;
import eu.venthe.pipeline.project.domain.source_configurations.plugins.template.model.SourceType;
import eu.venthe.pipeline.project.domain.source_configurations.plugins.template.ProjectSourcePlugin;
import eu.venthe.pipeline.shared_kernel.configuration_properties.ConfigurationPropertyDefinition;
import eu.venthe.pipeline.shared_kernel.configuration_properties.PropertyName;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

@Slf4j
class GerritProjectSourcePluginTest {
    @Test
    void validSourceType() {
        ProjectSourcePlugin plugin = new GerritProjectSourcePlugin();

        Assertions.assertThat(plugin.getSourceType())
                .isEqualTo(new SourceType("gerrit"));
    }

    @Test
    void validPropertyDefinitions() {
        ProjectSourcePlugin plugin = new GerritProjectSourcePlugin();

        Map<PropertyName, ConfigurationPropertyDefinition> definitions = plugin.getConfigurationPropertiesDefinitions();

        Assertions.assertThat(definitions)
                .hasSize(3)
                .containsExactlyInAnyOrderEntriesOf(Map.of(
                        new PropertyName("basePath"), ConfigurationPropertyDefinition.simpleText(),
                        new PropertyName("username"), ConfigurationPropertyDefinition.simpleText(),
                        new PropertyName("password"), ConfigurationPropertyDefinition.builder().asText().masked(true).build()
                ));
    }
}
