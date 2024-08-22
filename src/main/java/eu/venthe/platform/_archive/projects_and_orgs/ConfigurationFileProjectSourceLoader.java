/*
package eu.venthe.pipeline.orchestrator.repositorys.api.file;

import eu.venthe.pipeline.orchestrator.repositorys.application.RepositorySourceConfigurationDto;
import eu.venthe.pipeline.orchestrator.repositorys.application.RepositorySourceConfigurationService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
// FIXME: Remove
//  We need to have a listener before we send the event out
@DependsOn("repositoryConfigurationSourceEventListener")
public class ConfigurationFileRepositorySourceLoader {
    private final RepositorySourceConfigurationService repositorysSourceConfigurationService;
    private final RepositorySourcesConfiguration configuration;

    @PostConstruct
    void loadFromConfiguration() {
        log.info("Loading repository configuration from file. {}", configuration);
        Optional.ofNullable(configuration.getRepositorySources()).ifPresent(el -> el.forEach((name, value) -> {
            String sourcePluginId = value.getSourcePluginId();
            Map<String, String> properties = value.getProperties();
            repositorysSourceConfigurationService.addRepositorySourceConfiguration(new RepositorySourceConfigurationDto(name, sourcePluginId, properties));
        }));
    }
}
*/
