package eu.venthe.pipeline.orchestrator;

import eu.venthe.pipeline.orchestrator.project_configuration_sources.core.application.ProjectSourceConfigurationDto;
import eu.venthe.pipeline.orchestrator.project_configuration_sources.core.application.ProjectsSourceConfigurationService;
import eu.venthe.pipeline.orchestrator.projects.application.ProjectDto;
import eu.venthe.pipeline.orchestrator.projects.application.ProjectsService;
import eu.venthe.pipeline.orchestrator.utilities.TestProject;
import eu.venthe.pipeline.orchestrator.utilities.TestProjectSource;
import org.assertj.core.api.Assertions;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.test.context.TestPropertySource;

import java.util.Collection;
import java.util.Collections;

@TestPropertySource(properties = {
        "togglz.features.PROJECTS_SOURCE_CONFIGURATION_FACTORY_WIP.enabled=true",
        "togglz.features.PROJECTS_SERVICE_WIP.enabled=true",
        "plugins.jobExecutor.type=test",
        "plugins.projectSources.test.sourcePluginId=test",
})
class ProjectIntegrationTest extends AbstractIntegrationTest {
    @Autowired
    ProjectsSourceConfigurationService projectsSourceConfigurationService;

    @Autowired
    ProjectsService projectsService;

    @Autowired
    TestProjectSource testProjectSource;

    @Test
    void name() {
        testProjectSource.addProject(TestProject.builder().name("Test project").build());

        Collection<ProjectDto> projects = projectsService.listProjects();

        Awaitility.await().untilAsserted(() ->
                Assertions.assertThat(projects).singleElement().isEqualTo(new ProjectDto("Test project"))
        );
    }
}
