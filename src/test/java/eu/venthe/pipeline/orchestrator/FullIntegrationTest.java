package eu.venthe.pipeline.orchestrator;

import eu.venthe.pipeline.orchestrator.configuration.TestJobExecutorConfiguration;
import eu.venthe.pipeline.orchestrator.configuration.TestProjectSourcePluginConfiguration;
import eu.venthe.pipeline.orchestrator.organizations.application.*;
import eu.venthe.pipeline.orchestrator.organizations.domain.OrganizationId;
import eu.venthe.pipeline.orchestrator.organizations.domain.projects.ProjectId;
import eu.venthe.pipeline.orchestrator.organizations.domain.projects.ProjectStatus;
import eu.venthe.pipeline.orchestrator.organizations.domain.source_configurations.SourceConfigurationId;
import eu.venthe.pipeline.orchestrator.organizations.domain.source_configurations.plugins.template.model.ProjectDto;
import eu.venthe.pipeline.orchestrator.organizations.domain.source_configurations.plugins.template.model.SourceType;
import eu.venthe.pipeline.orchestrator.shared_kernel.configuration_properties.SuppliedProperties;
import eu.venthe.pipeline.orchestrator.workflow_executions.domain.job_executions.adapters.template.model.AdapterId;
import eu.venthe.pipeline.orchestrator.workflow_executions.domain.job_executions.adapters.template.model.AdapterType;
import eu.venthe.pipeline.orchestrator.workflow_executions.application.dto.ExecutionDetailsDto;
import eu.venthe.pipeline.orchestrator.workflow_executions.application.ExecutorManager;
import eu.venthe.pipeline.orchestrator.workflow_executions.application.JobExecutorCallbackService;
import eu.venthe.pipeline.orchestrator.workflow_executions.application.JobExecutorQueryService;
import eu.venthe.pipeline.orchestrator.workflow_executions.domain.job_executions.model.RunnerDimensions;
import eu.venthe.pipeline.orchestrator.workflow_executions.domain.model.JobExecutionId;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;

import java.io.File;
import java.util.Optional;
import java.util.stream.Stream;

import static eu.venthe.pipeline.orchestrator.configuration.TestJobExecutorConfiguration.setupExecution;
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
    @Autowired
    JobExecutorCallbackService callbackService;

    @Autowired
    TestProjectSourcePluginConfiguration.TestProjectSourcePluginPluginInstance projectSource;
    @Autowired
    TestJobExecutorConfiguration.TestJobExecutorAdapterAdapterInstance testJobExecutorAdapterAdapterInstance;

    @Test
    void name() {
        Mockito.when(projectSource.getProjectIds()).thenReturn(Stream.of(new ProjectDto.Id("Example-Project")));
        Mockito.when(projectSource.getProject("Example-Project")).thenReturn(Optional.of(new ProjectDto("Example-Project", ProjectStatus.ACTIVE)));

        var createOrganizationSpecification = CreateOrganizationSpecification.builder()
                .organizationId(new OrganizationId("default"))
                .build();
        var defaultOrganization = organizationCommandService.create(createOrganizationSpecification);
        var sourceConfigurationId = organizationCommandService.addSourceConfiguration(defaultOrganization, new SourceConfigurationId("default"), new SourceType("test"), SuppliedProperties.none());

        var defaultExecutor = executorManager.registerAdapter(defaultOrganization, new AdapterId("default"), new AdapterType("test"));
        var defaultRunner = executorManager.registerRunner(defaultExecutor, RunnerDimensions.none());

        // FIXME: This should be done scheduled & asynchronously
        projectSourcesManager.synchronize(sourceConfigurationId);

        // At this point, auto synchronization should happen. Let's wait for it.
        await("Synchronization done")
                .until(() -> !projectsQueryService.getProjectIds(sourceConfigurationId).collect(toSet()).isEmpty());

        final var projectId = ProjectId.of(sourceConfigurationId, "Example-Project");
        await("Project found")
                .untilAsserted(() -> assertThat(projectsQueryService.find(projectId)).isPresent());

        setupExecution(testJobExecutorAdapterAdapterInstance, metadata -> {
            System.out.println(metadata);
        });

        JobExecutionId executionId = projectsCommandService.executeManualWorkflow(projectId, "main", new File("example.yaml"));

        await("Execution done").untilAsserted(() ->
                Assertions.assertThat(jobExecutorQueryService.getExecutionDetails(executionId)).isEqualTo(new ExecutionDetailsDto()));
    }
}
