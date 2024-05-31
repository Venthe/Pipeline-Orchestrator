package eu.venthe.pipeline.orchestrator.projects.domain;

import eu.venthe.pipeline.orchestrator.projects.projects.api.CreateProjectSpecificationDto;
import eu.venthe.pipeline.orchestrator.projects.projects.application.ProjectsCommandService;
import eu.venthe.pipeline.orchestrator.projects.projects.application.ProjectsQueryService;
import eu.venthe.pipeline.orchestrator.projects.source_configuration.domain.ProjectsSourceConfiguration;
import eu.venthe.pipeline.orchestrator.projects.source_configuration.domain.ProjectsSourceConfigurationId;
import eu.venthe.pipeline.orchestrator.projects.plugins.template.ProjectSourcePlugin;
import eu.venthe.pipeline.orchestrator.projects.plugins.template.model.ProjectDto;
import eu.venthe.pipeline.orchestrator.projects.domain.projects.ProjectId;
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
    @Mock
    ProjectsCommandService projectCommands;
    @Spy
    ProjectsQueryService projectQueries;

    ProjectsSourceConfiguration projectsSourceConfiguration;

    @BeforeEach
    void beforeEach() {
        projectsSourceConfiguration = new ProjectsSourceConfiguration(new ProjectsSourceConfigurationId("TestName"), pluginInstance);
    }

    @Test
    void name() {
        Mockito.when(pluginInstance.getProjectIds()).thenReturn(Stream.of(new ProjectDto.Id("123")));
        Mockito.when(projectQueries.getProjectIds(new ProjectsSourceConfigurationId("TestName"))).thenReturn(Stream.empty());
        Mockito.when(pluginInstance.getProject("123")).thenReturn(Optional.of(new ProjectDto("123", ProjectStatus.ACTIVE)));

        projectsSourceConfiguration.synchronize(projectCommands, projectQueries);

        Mockito.verify(projectCommands).add(new CreateProjectSpecificationDto(ProjectId.of(new ProjectsSourceConfigurationId("TestName"), "123"), ProjectStatus.ACTIVE));
    }

    @Test
    void name2() {
        ProjectDto expectedValue = new ProjectDto("123", ProjectStatus.ACTIVE);
        Mockito.when(pluginInstance.getProject("123")).thenReturn(Optional.of(expectedValue));

        var project = projectsSourceConfiguration.getProject("123");

        Assertions.assertThat(project).isPresent().hasValue(expectedValue);
    }
}
