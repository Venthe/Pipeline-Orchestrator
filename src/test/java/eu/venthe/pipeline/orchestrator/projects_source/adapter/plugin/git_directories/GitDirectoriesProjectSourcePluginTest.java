package eu.venthe.pipeline.orchestrator.projects_source.adapter.plugin.git_directories;

import eu.venthe.pipeline.orchestrator.projects_source.adapter.template.ProjectSourcePlugin;
import eu.venthe.pipeline.orchestrator.projects_source.adapter.template.model.ConfigurationProperty;
import eu.venthe.pipeline.orchestrator.projects_source.adapter.template.model.ConfigurationPropertyDefinition;
import eu.venthe.pipeline.orchestrator.projects_source.adapter.template.model.ProjectDto;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GitDirectoriesProjectSourcePluginTest {
    private final static Map<String, ConfigurationProperty> NO_PROPERTIES = Map.of();

    private final ProjectSourcePlugin projectSourcePlugin = new GitDirectoriesProjectSourcePlugin();

    @Mock
    private ProjectSourcePlugin.Api api;

    @Test
    void verifyConfigurationProperties() {
        var configurationProperties = projectSourcePlugin.getConfigurationProperties();
        assertThat(configurationProperties)
                .isEmpty();
    }

    @Test
    void projectSourcePlugin_canBeInstantiated() {
        ThrowableAssert.ThrowingCallable instantiate = () -> projectSourcePlugin.instantiate(NO_PROPERTIES, api);

        assertThatNoException()
                .isThrownBy(instantiate);
    }

    @Test
    void name() {
        var projectSource = instantiate();

        Collection<ProjectDto> projects = projectSource.getProjectsProvider().getProjects();

        Assertions.assertThat(projects).hasSize(2);
    }

    private ProjectSourcePlugin.Definition instantiate() {
        return projectSourcePlugin.instantiate(NO_PROPERTIES, api);
    }
}
