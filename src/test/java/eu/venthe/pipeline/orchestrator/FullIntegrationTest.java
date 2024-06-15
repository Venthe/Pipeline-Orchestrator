package eu.venthe.pipeline.orchestrator;

import eu.venthe.pipeline.orchestrator.organizations.application.*;
import eu.venthe.pipeline.orchestrator.organizations.domain.OrganizationId;
import eu.venthe.pipeline.orchestrator.organizations.domain.projects.ProjectId;
import eu.venthe.pipeline.orchestrator.organizations.domain.source_configurations.SourceConfigurationId;
import eu.venthe.pipeline.orchestrator.organizations.domain.source_configurations.plugins.template.model.SourceType;
import eu.venthe.pipeline.orchestrator.shared_kernel.configuration_properties.SuppliedProperties;
import eu.venthe.pipeline.orchestrator.workflow_executions.domain.job_executions.adapters.template.model.AdapterId;
import eu.venthe.pipeline.orchestrator.workflow_executions.domain.job_executions.adapters.template.model.AdapterType;
import eu.venthe.pipeline.orchestrator.workflow_executions.domain.job_executions.application.ExecutionDetailsDto;
import eu.venthe.pipeline.orchestrator.workflow_executions.domain.job_executions.application.ExecutorManager;
import eu.venthe.pipeline.orchestrator.workflow_executions.domain.job_executions.application.JobExecutorQueryService;
import eu.venthe.pipeline.orchestrator.workflow_executions.domain.job_executions.application.runner.Architecture;
import eu.venthe.pipeline.orchestrator.workflow_executions.domain.job_executions.application.runner.ContainerImage;
import eu.venthe.pipeline.orchestrator.workflow_executions.domain.job_executions.application.runner.OperatingSystem;
import eu.venthe.pipeline.orchestrator.workflow_executions.domain.job_executions.application.runner.RunnerDimensions;
import eu.venthe.pipeline.orchestrator.workflow_executions.domain.job_executions.domain.model.ExecutionId;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;

import java.io.File;

import static java.util.stream.Collectors.toSet;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

@TestPropertySource(properties = {
        "logging.level.eu.venthe=TRACE"
})
class FullIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    ExecutorManager executorManager;
    @Autowired
    ProjectSourcesManager projectSourcesManager;
    @Autowired
    ProjectsCommandService projectsCommandService;
    @Autowired
    ProjectsQueryService projectsQueryService;
    @Autowired
    JobExecutorQueryService jobExecutorQueryService;
    @Autowired
    OrganizationCommandService organizationCommandService;

    @Test
    void name() {
        var createOrganizationSpecification = CreateOrganizationSpecification.builder()
                .organizationId(new OrganizationId("default"))
                .build();
        var defaultOrganization = organizationCommandService.create(createOrganizationSpecification);
        var sourceConfigurationId = organizationCommandService.addSourceConfiguration(defaultOrganization, new SourceConfigurationId("default"), new SourceType("gerrit"),
                SuppliedProperties.builder()
                        .property("basePath", "http://localhost:15480")
                        .property("username", "admin")
                        .property("password", "secret")
                        .build());

        var defaultExecutor = executorManager.registerAdapter(defaultOrganization, new AdapterId("default"), new AdapterType("docker"));
        var defaultRunner = executorManager.registerRunner(defaultExecutor,
                RunnerDimensions.builder()
                        .dimension(OperatingSystem.LINUX)
                        .dimension(Architecture.X64)
                        .dimension(new ContainerImage("docker.home.arpa/venthe/ubuntu-runner:23.10"))
                        .build());

        // FIXME: This should be done scheduled & asynchronously
        projectSourcesManager.synchronize(sourceConfigurationId);

        // At this point, auto synchronization should happen. Let's wait for it.
        await("Synchronization done")
                .until(() -> !projectsQueryService.getProjectIds(sourceConfigurationId).collect(toSet()).isEmpty());

        final var projectId = ProjectId.of(sourceConfigurationId, "Example-Project");
        await("Project found")
                .untilAsserted(() -> assertThat(projectsQueryService.find(projectId)).isPresent());

        ExecutionId executionId = projectsCommandService.executeManualWorkflow(projectId, "main", new File("example.yaml"));

        // await().timeout(Duration.ofDays(1)).until(() -> false);

        await("Execution done").untilAsserted(() ->
                Assertions.assertThat(jobExecutorQueryService.getExecutionDetails(executionId)).isEqualTo(new ExecutionDetailsDto()));
    }
}
