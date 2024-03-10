package eu.venthe.pipeline.orchestrator.project_configuration_sources.driving_adapter;

import eu.venthe.pipeline.orchestrator.project_configuration_sources.core.application.ProjectSourceConfigurationDto;
import eu.venthe.pipeline.orchestrator.project_configuration_sources.core.application.ProjectsSourceConfigurationService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
// FIXME: Remove
//  We need to have a listener before we send the event out
@DependsOn("projectConfigurationSourceEventListener")
public class ConfigurationFileProjectSourceLoader {
    private final ProjectsSourceConfigurationService projectsSourceConfigurationService;
    private final ProjectSourcesConfiguration configuration;

    @PostConstruct
    void loadFromConfiguration() {
        log.info("Loading project configuration from file. {}", configuration);
        configuration.getProjectSources().forEach((name, value) -> {
            String sourcePluginId = value.getSourcePluginId();
            Map<String, String> properties = value.getProperties();
            projectsSourceConfigurationService.addProjectSourceConfiguration(new ProjectSourceConfigurationDto(name, sourcePluginId, properties));
        });
    }
}
