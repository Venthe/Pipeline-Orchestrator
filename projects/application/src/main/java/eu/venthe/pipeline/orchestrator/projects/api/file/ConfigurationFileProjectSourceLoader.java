package eu.venthe.pipeline.orchestrator.projects.api.file;

import eu.venthe.pipeline.orchestrator.projects.application.ProjectSourceConfigurationDto;
import eu.venthe.pipeline.orchestrator.projects.application.ProjectsSourceConfigurationService;
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
@DependsOn("projectConfigurationSourceEventListener")
public class ConfigurationFileProjectSourceLoader {
    private final ProjectsSourceConfigurationService projectsSourceConfigurationService;
    private final ProjectSourcesConfiguration configuration;

    @PostConstruct
    void loadFromConfiguration() {
        log.info("Loading project configuration from file. {}", configuration);
        Optional.ofNullable(configuration.getProjectSources()).ifPresent(el -> el.forEach((name, value) -> {
            String sourcePluginId = value.getSourcePluginId();
            Map<String, String> properties = value.getProperties();
            projectsSourceConfigurationService.addProjectSourceConfiguration(new ProjectSourceConfigurationDto(name, sourcePluginId, properties));
        }));
    }
}
