package eu.venthe.pipeline.orchestrator;

import eu.venthe.pipeline.orchestrator.projects.api.ProjectDto;
import eu.venthe.pipeline.orchestrator.projects.api.ProjectsQueryService;
import eu.venthe.pipeline.orchestrator.projects_source.api.ProjectsSourceConfigurationService;
import eu.venthe.pipeline.orchestrator.projects_source.api.ReadProjectSourceConfigurationDto;
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
    ProjectsQueryService projectsService;

    @Test
    void name() {
        Map<String, String> properties = Map.of(
                "username", "admin",
                "password", "secret",
                "basePath", "http://localhost:15480"
        );
        String id = "gerrit_1";
        String sourceType = "gerrit";
        String projectId = projectsSourceConfigurationService.addProjectSourceConfiguration(id, sourceType, properties);

        Set<ReadProjectSourceConfigurationDto> projectConfigurations = projectsSourceConfigurationService.listConfigurations();
        await().untilAsserted(() -> {
            assertThat(projectConfigurations).singleElement()
                    .isEqualTo(new ReadProjectSourceConfigurationDto(id, sourceType));
        });

        projectsSourceConfigurationService.synchronizeProjects(projectId);

        await().untilAsserted(() -> {
            Collection<ProjectDto> projects = projectsService.listProjects();
            assertThat(projects).hasSize(2).containsExactlyInAnyOrder(
                    new ProjectDto("All-Projects", id),
                    new ProjectDto("All-Users", id)
            );
        });
    }
}
