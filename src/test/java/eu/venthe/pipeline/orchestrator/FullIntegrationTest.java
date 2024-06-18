package eu.venthe.pipeline.orchestrator;

import eu.venthe.pipeline.orchestrator.configuration.MockBeanConfiguration;
import eu.venthe.pipeline.orchestrator.modules.workflow.application.ExecutionAdapterManager;
import eu.venthe.pipeline.orchestrator.modules.workflow.application.JobExecutionQueryService;
import eu.venthe.pipeline.orchestrator.modules.workflow.application.JobExecutorCallbackService;
import eu.venthe.pipeline.orchestrator.modules.workflow.application.WorkflowExecutionCommandService;
import eu.venthe.pipeline.orchestrator.modules.workflow.domain.model.WorkflowExecutionId;
import eu.venthe.pipeline.orchestrator.organizations.application.*;
import eu.venthe.pipeline.orchestrator.organizations.application.dto.CreateOrganizationSpecification;
import eu.venthe.pipeline.orchestrator.organizations.domain.OrganizationId;
import eu.venthe.pipeline.orchestrator.projects.domain.ProjectId;
import eu.venthe.pipeline.orchestrator.projects.domain.ProjectStatus;
import eu.venthe.pipeline.orchestrator.projects.application.impl.ProjectSourcesManager;
import eu.venthe.pipeline.orchestrator.projects.application.ProjectsCommandService;
import eu.venthe.pipeline.orchestrator.projects.application.ProjectsQueryService;
import eu.venthe.pipeline.orchestrator.projects.domain.source_configurations.SourceConfigurationId;
import eu.venthe.pipeline.orchestrator.projects.domain.source_configurations.plugins.template.model.ProjectDto;
import eu.venthe.pipeline.orchestrator.projects.domain.source_configurations.plugins.template.model.SourceType;
import eu.venthe.pipeline.orchestrator.shared_kernel.configuration_properties.SuppliedProperties;
import eu.venthe.pipeline.orchestrator.modules.workflow.domain.job_executions.adapters.template.model.AdapterId;
import eu.venthe.pipeline.orchestrator.modules.workflow.domain.job_executions.adapters.template.model.AdapterType;
import eu.venthe.pipeline.orchestrator.modules.workflow.domain.job_executions.model.RunnerDimensions;
import eu.venthe.pipeline.orchestrator.shared_kernel.git.Revision;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;

import java.io.File;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.stream.Stream;

import static eu.venthe.pipeline.orchestrator.configuration.MockBeanConfiguration.setupExecution;
import static java.util.stream.Collectors.toSet;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

@TestPropertySource(properties = {
        "logging.level.eu.venthe=TRACE"
})
class FullIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    ExecutionAdapterManager executionAdapterManager;
    @Autowired
    ProjectSourcesManager projectSourcesManager;
    @Autowired
    ProjectsCommandService projectsCommandService;
    @Autowired
    ProjectsQueryService projectsQueryService;
    @Autowired
    JobExecutionQueryService jobExecutionQueryService;
    @Autowired
    OrganizationCommandService organizationCommandService;
    @Autowired
    JobExecutorCallbackService callbackService;
    @Autowired
    WorkflowExecutionCommandService workflowExecutionCommandService;

    @Autowired
    MockBeanConfiguration.MockProjectSource projectSource;
    @Autowired
    MockBeanConfiguration.MockAdapterInstance adapterInstance;

    @Test
    void name() {
        Mockito.when(projectSource.getProjectIds()).thenReturn(Stream.of(new ProjectDto.Id("Example-Project")));
        Mockito.when(projectSource.getProject("Example-Project")).thenReturn(Optional.of(new ProjectDto("Example-Project", ProjectStatus.ACTIVE)));

        final var createOrganizationSpecification = CreateOrganizationSpecification.builder()
                .organizationId(new OrganizationId("default"))
                .build();
        final var defaultOrganization = organizationCommandService.create(createOrganizationSpecification);
        final var sourceConfigurationId = organizationCommandService.addSourceConfiguration(defaultOrganization, new SourceConfigurationId("default"), new SourceType("test"), SuppliedProperties.none());

        final var defaultExecutor = executionAdapterManager.registerAdapter(defaultOrganization, new AdapterId("default"), new AdapterType("test"));
        final var defaultRunner = executionAdapterManager.registerRunner(defaultExecutor, RunnerDimensions.none());

        // FIXME: This should be done scheduled & asynchronously
        projectSourcesManager.synchronize(sourceConfigurationId);

        // At this point, auto synchronization should happen. Let's wait for it.
        await("Synchronization done")
                .until(() -> !projectsQueryService.getProjectIds(sourceConfigurationId).collect(toSet()).isEmpty());

        final var projectId = ProjectId.of(sourceConfigurationId, "Example-Project");
        await("Project found")
                .untilAsserted(() -> assertThat(projectsQueryService.find(projectId)).isPresent());

        final var revision = new Revision("main");

        projectsCommandService.registerTrackedRevision(projectId, revision);

        setupExecution(adapterInstance, metadata -> {
            System.out.println(metadata);
        });

        final var workflowExecutionId = workflowExecutionCommandService.triggerManualWorkflow(projectId, revision, Paths.get("test-workflow.yml"));

       /*
       JobExecutionId executionId = projectsCommandService.executeManualWorkflow(projectId, "main", new File("example.yaml"));

        await("Execution done").untilAsserted(() ->
                Assertions.assertThat(jobExecutionQueryService.getExecutionDetails(executionId)).isEqualTo(new ExecutionDetailsDto()));
       */
    }
}
