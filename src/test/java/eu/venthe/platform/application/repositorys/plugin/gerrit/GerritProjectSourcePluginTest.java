package eu.venthe.platform.application.repositorys.plugin.gerrit;

import eu.venthe.platform.source_configuration.domain.plugins.gerrit.GerritRepositorySourcePlugin;
import eu.venthe.platform.source_configuration.domain.model.SourceType;
import eu.venthe.platform.source_configuration.domain.plugins.template.RepositorySourcePlugin;
import eu.venthe.platform.shared_kernel.configuration_properties.ConfigurationPropertyDefinition;
import eu.venthe.platform.shared_kernel.configuration_properties.PropertyName;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

@Slf4j
class GerritRepositorySourcePluginTest {
    @Test
    void validSourceType() {
        RepositorySourcePlugin plugin = new GerritRepositorySourcePlugin();

        Assertions.assertThat(plugin.getSourceType())
                .isEqualTo(new SourceType("gerrit"));
    }

    @Test
    void validPropertyDefinitions() {
        RepositorySourcePlugin plugin = new GerritRepositorySourcePlugin();

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
