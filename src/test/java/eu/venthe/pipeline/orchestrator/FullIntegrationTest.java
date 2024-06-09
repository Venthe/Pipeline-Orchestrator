package eu.venthe.pipeline.orchestrator;

import eu.venthe.pipeline.orchestrator.workflow_executions.domain.job_executions.adapters.template.model.AdapterId;
import eu.venthe.pipeline.orchestrator.workflow_executions.domain.job_executions.adapters.template.model.AdapterType;
import eu.venthe.pipeline.orchestrator.workflow_executions.domain.job_executions.application.JobExecutorAdapterManager;
import eu.venthe.pipeline.orchestrator.workflow_executions.domain.job_executions.application.JobExecutorQueryService;
import eu.venthe.pipeline.orchestrator.workflow_executions.domain.job_executions.application.runner.RunnerDimensions;
import eu.venthe.pipeline.orchestrator.workflow_executions.domain.job_executions.domain.model.ExecutionId;
import eu.venthe.pipeline.orchestrator.projects.application.ProjectsCommandService;
import eu.venthe.pipeline.orchestrator.projects.application.ProjectsQueryService;
import eu.venthe.pipeline.orchestrator.projects.domain.model.ProjectId;
import eu.venthe.pipeline.orchestrator.organizations.domain.source_configuration.application.ProjectSourcesManager;
import eu.venthe.pipeline.orchestrator.organizations.domain.source_configuration.domain.model.ProjectsSourceConfigurationId;
import eu.venthe.pipeline.orchestrator.organizations.domain.source_configuration.plugins.template.model.SourceType;
import eu.venthe.pipeline.orchestrator.shared_kernel.configuration_properties.PropertyName;
import eu.venthe.pipeline.orchestrator.shared_kernel.configuration_properties.SuppliedProperties;
import eu.venthe.pipeline.orchestrator.shared_kernel.configuration_properties.TextSuppliedConfigurationProperty;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.time.Duration;

import static java.util.stream.Collectors.toSet;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

class FullIntegrationTest extends AbstractIntegrationTest {
    private static final ProjectsSourceConfigurationId EXAMPLE_CONFIGURATION_ID = new ProjectsSourceConfigurationId("Test");

    @Autowired
    JobExecutorAdapterManager jobExecutorAdapterManager;
    @Autowired
    ProjectSourcesManager projectSourcesManager;
    @Autowired
    ProjectsCommandService projectsCommandService;
    @Autowired
    ProjectsQueryService projectsQueryService;
    @Autowired
    JobExecutorQueryService jobExecutorQueryService;

    @Test
    void name() {
        AdapterId executorId = jobExecutorAdapterManager.registerExecutorAdapter(new AdapterId("docker-test"), new AdapterType("docker"), SuppliedProperties.none());
        var runnerId = jobExecutorAdapterManager.registerRunner(executorId, new RunnerDimensions.ContainerImage("docker.home.arpa/venthe/ubuntu-runner:23.10"), RunnerDimensions.OperatingSystem.LINUX.getValue(), RunnerDimensions.Architecture.X64.getValue());

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

        ExecutionId executionId = projectsCommandService.executeManualWorkflow(projectId, "main", new File("example.yaml"));

        await().timeout(Duration.ofDays(1)).until(() -> false);

        // await("Execution done").untilAsserted(() ->
        //         Assertions.assertThat(jobExecutorQueryService.getExecutionDetails(executionId)).isEqualTo(new ExecutionDetailsDto()));
    }
}
