package eu.venthe.pipeline.orchestrator;

import eu.venthe.pipeline.orchestrator.job_executor.adapters.template.model.AdapterId;
import eu.venthe.pipeline.orchestrator.job_executor.adapters.template.model.AdapterType;
import eu.venthe.pipeline.orchestrator.job_executor.application.ExecutionDetailsDto;
import eu.venthe.pipeline.orchestrator.job_executor.application.JobExecutorAdapterManager;
import eu.venthe.pipeline.orchestrator.job_executor.application.JobExecutorQueryService;
import eu.venthe.pipeline.orchestrator.job_executor.application.runner.ContainerId;
import eu.venthe.pipeline.orchestrator.job_executor.application.runner.JobExecutionRunner;
import eu.venthe.pipeline.orchestrator.job_executor.domain.model.ExecutionId;
import eu.venthe.pipeline.orchestrator.projects.projects.application.ProjectsCommandService;
import eu.venthe.pipeline.orchestrator.projects.projects.application.ProjectsQueryService;
import eu.venthe.pipeline.orchestrator.projects.projects.domain.model.ProjectId;
import eu.venthe.pipeline.orchestrator.projects.source_configuration.application.ProjectSourcesManager;
import eu.venthe.pipeline.orchestrator.projects.source_configuration.domain.model.ProjectsSourceConfigurationId;
import eu.venthe.pipeline.orchestrator.projects.source_configuration.plugins.template.model.SourceType;
import eu.venthe.pipeline.orchestrator.shared_kernel.configuration_properties.PropertyName;
import eu.venthe.pipeline.orchestrator.shared_kernel.configuration_properties.SuppliedProperties;
import eu.venthe.pipeline.orchestrator.shared_kernel.configuration_properties.TextSuppliedConfigurationProperty;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;

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
        AdapterId executorId = jobExecutorAdapterManager.registerExecutorAdapter(new AdapterId("docker-executor"), new AdapterType("docker"), SuppliedProperties.none());
        jobExecutorAdapterManager.setDefault(executorId);
        var runnerId = jobExecutorAdapterManager.registerRunnerForAdapter(executorId, new ContainerId("docker.home.arpa/venthe/ubuntu-runner:23.10"), JobExecutionRunner.OperatingSystem.LINUX, JobExecutionRunner.Architecture.X64);
        jobExecutorAdapterManager.setDefault(runnerId);

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

        await("Execution done").untilAsserted(() ->
                Assertions.assertThat(jobExecutorQueryService.getExecutionDetails(executionId)).isEqualTo(new ExecutionDetailsDto()));
    }
}
