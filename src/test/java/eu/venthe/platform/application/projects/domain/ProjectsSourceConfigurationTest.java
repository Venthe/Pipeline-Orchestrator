package eu.venthe.platform.application.projects.domain;

import eu.venthe.platform.project.application.ProjectsCommandService;
import eu.venthe.platform.project.application.ProjectsQueryService;
import eu.venthe.platform.project.application.dto.CreateProjectSpecificationDto;
import eu.venthe.platform.project.domain.ProjectId;
import eu.venthe.platform.project.domain.ProjectStatus;
import eu.venthe.platform.source_configuration.ProjectsSourceConfiguration;
import eu.venthe.platform.source_configuration.SourceConfigurationId;
import eu.venthe.platform.source_configuration.plugins.template.ProjectSourcePlugin;
import eu.venthe.platform.source_configuration.plugins.template.model.ProjectDto;
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
        projectsSourceConfiguration = ProjectsSourceConfiguration.reconstitute(new SourceConfigurationId("TestName"), pluginInstance, projectCommands, projectQueries);
    }

    @Test
    void name() {
        Mockito.when(pluginInstance.getProjectIds()).thenReturn(Stream.of(new ProjectDto.Id("123")));
        Mockito.when(projectQueries.getProjectIds(new SourceConfigurationId("TestName"))).thenReturn(Stream.empty());
        Mockito.when(pluginInstance.getProject("123")).thenReturn(Optional.of(new ProjectDto("123", ProjectStatus.ACTIVE)));

        // FIXME: Uncomment
        // projectsSourceConfiguration.synchronize();

        Mockito.verify(projectCommands).add(new SourceConfigurationId("TestName"), new CreateProjectSpecificationDto(ProjectId.builder().name("123").configurationId(new SourceConfigurationId("TestName")).build(), ProjectStatus.ACTIVE));
    }

    @Test
    void name2() {
        ProjectDto expectedValue = new ProjectDto("123", ProjectStatus.ACTIVE);
        Mockito.when(pluginInstance.getProject("123")).thenReturn(Optional.of(expectedValue));

        var project = projectsSourceConfiguration.getProject("123");

        Assertions.assertThat(project).isPresent().hasValue(expectedValue);
    }
}
