package eu.venthe.pipeline.orchestrator;

import eu.venthe.pipeline.orchestrator.projects.api.dto.ProjectDto;
import eu.venthe.pipeline.orchestrator.projects.api.ProjectsQueryService;
import eu.venthe.pipeline.orchestrator.projects_source._archive.api.ProjectsSourceConfigurationCommandService;
import eu.venthe.pipeline.orchestrator.projects_source._archive.api.ProjectsSourceConfigurationQueryService;
import eu.venthe.pipeline.orchestrator.projects_source._archive.api.ReadProjectSourceConfigurationDto;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

class ProjectsIntegrationTest extends AbstractIntegrationTest {
    @Autowired
    ProjectsSourceConfigurationCommandService projectsSourceConfigurationService;
    @Autowired
    ProjectsSourceConfigurationQueryService projectsSourceConfigurationQueryService;

    @Autowired
    ProjectsQueryService projectsService;

    @Test
    @Disabled
    void name() {
        Map<String, String> properties = Map.of(
                "username", "admin",
                "password", "secret",
                "basePath", "http://localhost:15480"
        );
        String id = "gerrit_1";
        String sourceType = "gerrit";
        String projectId = projectsSourceConfigurationService.addProjectSourceConfiguration(id, sourceType, properties);

        Set<ReadProjectSourceConfigurationDto> projectConfigurations = projectsSourceConfigurationQueryService.listConfigurations();
        await().untilAsserted(() -> {
            assertThat(projectConfigurations).singleElement()
                    .isEqualTo(new ReadProjectSourceConfigurationDto(id, sourceType));
        });

        projectsSourceConfigurationService.synchronizeProject(projectId);

        await().untilAsserted(() -> {
            Collection<ProjectDto> projects = projectsService.listProjects();
            assertThat(projects).hasSize(2).containsExactlyInAnyOrder(
                    new ProjectDto("All-Projects", id),
                    new ProjectDto("All-Users", id)
            );
        });
    }
}
