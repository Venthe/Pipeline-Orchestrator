package eu.venthe.pipeline.orchestrator.projects.domain;

import eu.venthe.pipeline.orchestrator.projects.api.dto.CreateProjectSpecificationDto;
import eu.venthe.pipeline.orchestrator.projects.application.ProjectsCommandService;
import eu.venthe.pipeline.orchestrator.projects.application.ProjectsQueryService;
import eu.venthe.pipeline.orchestrator.projects.domain.model.SourceType;
import eu.venthe.pipeline.orchestrator.projects.domain.plugin_template.ProjectSourcePlugin;
import eu.venthe.pipeline.orchestrator.projects.domain.plugin_template.model.ProjectDto;
import eu.venthe.pipeline.orchestrator.projects.domain.projects.model.ProjectStatus;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.stream.Stream;

@ExtendWith(MockitoExtension.class)
class ProjectsSourceConfigurationTest {
    @Mock
    ProjectSourcePlugin.PluginInstance pluginInstance;
    @Spy
    ProjectsCommandService projectCommands;
    @Spy
    ProjectsQueryService projectQueries;

    ProjectsSourceConfiguration projectsSourceConfiguration;

    @BeforeEach
    void beforeEach() {
        projectsSourceConfiguration = new ProjectsSourceConfiguration(new ProjectsSourceConfigurationId("Test"), pluginInstance);
    }

    @Test
    void name() {
        Mockito.when(pluginInstance.getProjects()).thenReturn(Stream.of(new ProjectDto("123", ProjectStatus.ACTIVE)));
        Mockito.when(projectQueries.getProjectIds(new ProjectsSourceConfigurationId("TestName"))).thenReturn(Stream.empty());

        projectsSourceConfiguration.synchronize(projectCommands, projectQueries);

        Mockito.verify(projectCommands).add(new CreateProjectSpecificationDto(ProjectId.of(new ProjectsSourceConfigurationId("TestName"), "123"), ProjectStatus.ACTIVE, null));
    }

    @Test
    void name2() {
        ProjectDto expectedValue = new ProjectDto("123", ProjectStatus.ACTIVE);
        Mockito.when(pluginInstance.getProject("123")).thenReturn(Optional.of(expectedValue));

        var project = projectsSourceConfiguration.getProject("123");

        Assertions.assertThat(project).isPresent().hasValue(expectedValue);
    }
}
