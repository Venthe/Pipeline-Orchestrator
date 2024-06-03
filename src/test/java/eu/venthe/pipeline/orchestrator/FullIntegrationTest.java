package eu.venthe.pipeline.orchestrator;

import eu.venthe.pipeline.orchestrator.projects.projects.application.ProjectsCommandService;
import eu.venthe.pipeline.orchestrator.projects.projects.application.ProjectsQueryService;
import eu.venthe.pipeline.orchestrator.projects.projects.domain.model.ProjectId;
import eu.venthe.pipeline.orchestrator.projects.source_configuration.application.ProjectSourcesManager;
import eu.venthe.pipeline.orchestrator.projects.source_configuration.domain.model.ProjectsSourceConfigurationId;
import eu.venthe.pipeline.orchestrator.projects.source_configuration.plugins.template.model.PropertyName;
import eu.venthe.pipeline.orchestrator.projects.source_configuration.plugins.template.model.SourceType;
import eu.venthe.pipeline.orchestrator.projects.source_configuration.plugins.template.model.SuppliedProperties;
import eu.venthe.pipeline.orchestrator.projects.source_configuration.plugins.template.model.TextSuppliedConfigurationProperty;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static java.util.stream.Collectors.toSet;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

class FullIntegrationTest extends AbstractIntegrationTest {
    private static final ProjectsSourceConfigurationId EXAMPLE_CONFIGURATION_ID = new ProjectsSourceConfigurationId("Test");

    @Autowired
    ProjectSourcesManager projectSourcesManager;
    @Autowired
    ProjectsCommandService projectsCommandService;
    @Autowired
    ProjectsQueryService projectsQueryService;

    @Test
    void name() {
        ProjectsSourceConfigurationId configurationId = projectSourcesManager.register(EXAMPLE_CONFIGURATION_ID, new SourceType("Gerrit"),
                SuppliedProperties.builder()
                        .property(new PropertyName("basePath"), new TextSuppliedConfigurationProperty("http://localhost:15480"))
                        .property(new PropertyName("username"), new TextSuppliedConfigurationProperty("admin"))
                        .property(new PropertyName("password"), new TextSuppliedConfigurationProperty("secret"))
                        .build());
        projectSourcesManager.synchronize(configurationId);

        // At this point, auto synchronization should happen. Let's wait for it.
        await("Synchronization done")
                .until(() -> !projectsQueryService.getProjectIds(EXAMPLE_CONFIGURATION_ID).collect(toSet()).isEmpty());

        final var projectId = ProjectId.of(EXAMPLE_CONFIGURATION_ID, "Example-Project");
        await("Project found")
                .untilAsserted(() -> assertThat(projectsQueryService.find(projectId)).isPresent());

        String executionId = projectsCommandService.executeManualWorkflow(projectId, "example.yaml", "main");
    }
}
