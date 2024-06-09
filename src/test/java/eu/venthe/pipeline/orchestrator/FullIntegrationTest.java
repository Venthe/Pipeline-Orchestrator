package eu.venthe.pipeline.orchestrator;

import eu.venthe.pipeline.orchestrator.organizations.application.CreateOrganizationSpecification;
import eu.venthe.pipeline.orchestrator.organizations.application.OrganizationCommandService;
import eu.venthe.pipeline.orchestrator.organizations.domain.OrganizationId;
import eu.venthe.pipeline.orchestrator.organizations.domain.application.ProjectSourcesManager;
import eu.venthe.pipeline.orchestrator.organizations.domain.domain.model.ProjectsSourceConfigurationId;
import eu.venthe.pipeline.orchestrator.organizations.domain.plugins.template.model.SourceType;
import eu.venthe.pipeline.orchestrator.organizations.domain.projects.application.ProjectsCommandService;
import eu.venthe.pipeline.orchestrator.organizations.domain.projects.application.ProjectsQueryService;
import eu.venthe.pipeline.orchestrator.shared_kernel.configuration_properties.SuppliedProperties;
import eu.venthe.pipeline.orchestrator.workflow_executions.domain.job_executions.adapters.template.model.AdapterId;
import eu.venthe.pipeline.orchestrator.workflow_executions.domain.job_executions.adapters.template.model.AdapterType;
import eu.venthe.pipeline.orchestrator.workflow_executions.domain.job_executions.application.ExecutorManager;
import eu.venthe.pipeline.orchestrator.workflow_executions.domain.job_executions.application.JobExecutorQueryService;
import eu.venthe.pipeline.orchestrator.workflow_executions.domain.job_executions.application.runner.Architecture;
import eu.venthe.pipeline.orchestrator.workflow_executions.domain.job_executions.application.runner.ContainerImage;
import eu.venthe.pipeline.orchestrator.workflow_executions.domain.job_executions.application.runner.OperatingSystem;
import eu.venthe.pipeline.orchestrator.workflow_executions.domain.job_executions.application.runner.RunnerDimensions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

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
        organizationCommandService.addSourceConfiguration(defaultOrganization, new ProjectsSourceConfigurationId("default"), new SourceType("gerrit"),
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

/*        // At this point, auto synchronization should happen. Let's wait for it.
        await("Synchronization done")
                .until(() -> !projectsQueryService.getProjectIds(new ProjectsSourceConfigurationId("Test")).collect(toSet()).isEmpty());

        final var projectId = ProjectId.of(new ProjectsSourceConfigurationId("Test"), "Example-Project");
        await("Project found")
                .untilAsserted(() -> assertThat(projectsQueryService.find(projectId)).isPresent());

        ExecutionId executionId = projectsCommandService.executeManualWorkflow(projectId, "main", new File("example.yaml"));

        await().timeout(Duration.ofDays(1)).until(() -> false);

        // await("Execution done").untilAsserted(() ->
        //         Assertions.assertThat(jobExecutorQueryService.getExecutionDetails(executionId)).isEqualTo(new ExecutionDetailsDto()));*/
    }
}
