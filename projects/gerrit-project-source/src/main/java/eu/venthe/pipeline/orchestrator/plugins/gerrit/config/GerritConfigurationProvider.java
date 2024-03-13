package eu.venthe.pipeline.orchestrator.plugins.gerrit.config;

import eu.venthe.pipeline.orchestrator.plugins.projects.ConfigurationProvider;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class GerritConfigurationProvider {
    private final Map<String, GerritBindingConfiguration> projectProviders;
    private final Map<String, GerritBindingConfiguration> versionControlProvider;

    public GerritConfigurationProvider(ConfigurationProvider configurationProvider) {
        projectProviders = configurationProvider.getConfigurationForType(ConfigurationProvider.ConfigurationType.PROJECT_PROVIDER, "gerrit", GerritBindingConfiguration::new);
        versionControlProvider = configurationProvider.getConfigurationForType(ConfigurationProvider.ConfigurationType.VERSION_CONTROL_SYSTEM, "gerrit", GerritBindingConfiguration::new);
    }

    public GerritBindingConfiguration getForId(String configurationId) {
        return Optional.ofNullable(projectProviders.get(configurationId)).orElseThrow();
    }

    public Map<String, GerritBindingConfiguration> getAllConfigurations() {
        return projectProviders;
    }
}
