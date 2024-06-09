package eu.venthe.pipeline.orchestrator.projects.domain;

import eu.venthe.pipeline.orchestrator.projects.api.CreateProjectSpecificationDto;
import eu.venthe.pipeline.orchestrator.projects.application.ProjectsCommandService;
import eu.venthe.pipeline.orchestrator.projects.application.ProjectsQueryService;
import eu.venthe.pipeline.orchestrator.projects.domain.model.ProjectId;
import eu.venthe.pipeline.orchestrator.projects.domain.model.ProjectStatus;
import eu.venthe.pipeline.orchestrator.organizations.domain.source_configuration.domain.ProjectsSourceConfiguration;
import eu.venthe.pipeline.orchestrator.organizations.domain.source_configuration.domain.model.ProjectsSourceConfigurationId;
import eu.venthe.pipeline.orchestrator.organizations.domain.source_configuration.plugins.template.ProjectSourcePlugin;
import eu.venthe.pipeline.orchestrator.organizations.domain.source_configuration.plugins.template.model.ProjectDto;
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
        projectsSourceConfiguration = ProjectsSourceConfiguration.reconstitute(new ProjectsSourceConfigurationId("TestName"), pluginInstance, projectCommands, projectQueries);
    }

    @Test
    void name() {
        Mockito.when(pluginInstance.getProjectIds()).thenReturn(Stream.of(new ProjectDto.Id("123")));
        Mockito.when(projectQueries.getProjectIds(new ProjectsSourceConfigurationId("TestName"))).thenReturn(Stream.empty());
        Mockito.when(pluginInstance.getProject("123")).thenReturn(Optional.of(new ProjectDto("123", ProjectStatus.ACTIVE)));

        projectsSourceConfiguration.synchronize();

        Mockito.verify(projectCommands).add(new ProjectsSourceConfigurationId("TestName"), new CreateProjectSpecificationDto(ProjectId.of(new ProjectsSourceConfigurationId("TestName"), "123"), ProjectStatus.ACTIVE));
    }

    @Test
    void name2() {
        ProjectDto expectedValue = new ProjectDto("123", ProjectStatus.ACTIVE);
        Mockito.when(pluginInstance.getProject("123")).thenReturn(Optional.of(expectedValue));

        var project = projectsSourceConfiguration.getProject("123");

        Assertions.assertThat(project).isPresent().hasValue(expectedValue);
    }
}
