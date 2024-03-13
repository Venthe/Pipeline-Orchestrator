package eu.venthe.pipeline.orchestrator;

import eu.venthe.pipeline.orchestrator.projects.application.ProjectDto;
import eu.venthe.pipeline.orchestrator.plugins.projects.ProjectSourceConfigurationDto;
import eu.venthe.pipeline.orchestrator.projects.application.ProjectsService;
import eu.venthe.pipeline.orchestrator.projects.application.ProjectsSourceConfigurationService;
import eu.venthe.pipeline.orchestrator.projects.domain.ProjectSourceConfigurationId;
import org.assertj.core.api.Assertions;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

class ProjectsIntegrationTest extends AbstractIntegrationTest {
    @Autowired
    ProjectsSourceConfigurationService projectsSourceConfigurationService;

    @Autowired
    ProjectsService projectsService;

    @Test
    void name() {
        ProjectSourceConfigurationDto configurationDto = ProjectSourceConfigurationDto.builder()
                .sourceType("gerrit")
                .id("gerrit_1")
                .properties(Map.of(
                        "username", "admin",
                        "password", "secret",
                        "basePath", "http://localhost:15480"
                ))
                .build();
        ProjectSourceConfigurationId id = projectsSourceConfigurationService.addProjectSourceConfiguration(configurationDto);

        Set<ProjectSourceConfigurationDto> projectConfigurations = projectsSourceConfigurationService.listConfigurations();
        Assertions.assertThat(projectConfigurations).singleElement().isEqualTo(configurationDto);

        projectsSourceConfigurationService.synchronizeProjects(id);

        Awaitility.await().untilAsserted(() -> {
            Collection<ProjectDto> projects = projectsService.listProjects();
            Assertions.assertThat(projects).hasSize(2).containsExactlyInAnyOrder(
                    ProjectDto.builder().sourceId("gerrit_1").name("All-Projects").build()
            );
        });
    }
}
