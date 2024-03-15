package eu.venthe.pipeline.orchestrator;

import eu.venthe.pipeline.orchestrator.plugins.projects.ReadProjectSourceConfigurationDto;
import eu.venthe.pipeline.orchestrator.projects.projects.application.ProjectDto;
import eu.venthe.pipeline.orchestrator.projects.projects.application.ProjectsService;
import eu.venthe.pipeline.orchestrator.projects.configuration.application.ProjectsSourceConfigurationService;
import eu.venthe.pipeline.orchestrator.projects.configuration.domain.ProjectSourceConfigurationId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

class ProjectsIntegrationTest extends AbstractIntegrationTest {
    @Autowired
    ProjectsSourceConfigurationService projectsSourceConfigurationService;

    @Autowired
    ProjectsService projectsService;

    @Test
    void name() {
        Map<String, String> properties = Map.of(
                "username", "admin",
                "password", "secret",
                "basePath", "http://localhost:15480"
        );
        String id = "gerrit_1";
        String sourceType = "gerrit";
        ProjectSourceConfigurationId projectId = projectsSourceConfigurationService.addProjectSourceConfiguration(id, sourceType, properties);

        Set<ReadProjectSourceConfigurationDto> projectConfigurations = projectsSourceConfigurationService.listConfigurations();
        await().untilAsserted(() -> {
            assertThat(projectConfigurations).singleElement()
                    .isEqualTo(new ReadProjectSourceConfigurationDto(id, sourceType));
        });

        projectsSourceConfigurationService.synchronizeProjects(projectId);

        await().untilAsserted(() -> {
            Collection<ProjectDto> projects = projectsService.listProjects();
            assertThat(projects).hasSize(2).containsExactlyInAnyOrder(
                    ProjectDto.builder().sourceId(id).name("All-Projects").build(),
                    ProjectDto.builder().sourceId(id).name("All-Users").build()
            );
        });
    }
}
