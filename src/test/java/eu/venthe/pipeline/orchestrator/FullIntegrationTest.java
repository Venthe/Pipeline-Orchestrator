package eu.venthe.pipeline.orchestrator;

import eu.venthe.pipeline.orchestrator.fixtures.MockAdapterFixture;
import eu.venthe.pipeline.orchestrator.fixtures.MockProjectSourceFixture;
import eu.venthe.pipeline.orchestrator.modules.ProjectModuleMediator;
import eu.venthe.pipeline.orchestrator.modules.automation.runners.ExecutionAdapterManager;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.JobExecutionQueryService;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.JobExecutorCallbackService;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.WorkflowExecutionCommandService;
import eu.venthe.pipeline.orchestrator.modules.automation.runners.RegisterAdapterSpecification;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.execution.model.RunnerDimensions;
import eu.venthe.pipeline.orchestrator.organizations.application.OrganizationCommandService;
import eu.venthe.pipeline.orchestrator.organizations.application.dto.CreateOrganizationSpecification;
import eu.venthe.pipeline.orchestrator.organizations.application.dto.SourceConfigurationSpecification;
import eu.venthe.pipeline.orchestrator.projects.application.ProjectsCommandService;
import eu.venthe.pipeline.orchestrator.projects.application.ProjectsQueryService;
import eu.venthe.pipeline.orchestrator.projects.application.impl.ProjectSourcesManager;
import eu.venthe.pipeline.orchestrator.projects.domain.ProjectId;
import eu.venthe.pipeline.orchestrator.projects.domain.ProjectStatus;
import eu.venthe.pipeline.orchestrator.projects.domain.source_configurations.plugins.template.model.ProjectDto;
import eu.venthe.pipeline.orchestrator.shared_kernel.git.Revision;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;

import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

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
    ProjectModuleMediator projectModuleMediator;
    @Autowired
    WorkflowExecutionCommandService workflowExecutionCommandService;

    @Autowired
    MockProjectSourceFixture projectSourceFixture;
    @Autowired
    MockAdapterFixture adapterFixture;

    @BeforeEach
    void setup() {
    }

    @Test
    void name() {
        val projectName = "Example-Project";
        val requestedOrganizationId = "default";
        val sourceType = "test";
        val requestedSourceConfigurationId = "default";
        val requestedAdapterId = "default";
        val adapterType = "default";

        projectSourceFixture.onInstance(projectSource -> {
            when(projectSource.getProjectIds()).thenReturn(Stream.of(new ProjectDto.Id(projectName)));
            when(projectSource.getProject(projectName)).thenReturn(Optional.of(new ProjectDto(projectName, ProjectStatus.ACTIVE)));
            when(projectSource.getFile(eq(projectName), eq("main"), eq(Path.of(".mantle", "workflows", "test-workflow.yml"))))
                    .thenReturn(Optional.of("""
                            name: Test
                            on: workflow_dispatch
                            """.getBytes(StandardCharsets.UTF_8)));
        });

        val organizationSpecification = CreateOrganizationSpecification.builder()
                .organizationId(requestedOrganizationId)
                .build();
        final var organizationId = organizationCommandService.create(organizationSpecification);

        val sourceSpecification = SourceConfigurationSpecification.builder()
                .organizationId(organizationId)
                .sourceType(sourceType)
                .configurationId(requestedSourceConfigurationId)
                .build();
        val sourceConfigurationId = organizationCommandService.addSourceConfiguration(sourceSpecification);

        val adapterSpecification = RegisterAdapterSpecification.builder()
                .organizationId(organizationId)
                .adapterId(requestedAdapterId)
                .adapterType(adapterType)
                .build();
        val defaultExecutor = executionAdapterManager.registerAdapter(adapterSpecification);

        val defaultRunner = executionAdapterManager.registerRunner(defaultExecutor, RunnerDimensions.none());

        // FIXME: This should be done scheduled & asynchronously
        projectSourcesManager.synchronize(sourceConfigurationId);

        // At this point, auto synchronization should happen. Let's wait for it.
        await("Synchronization done")
                .until(() -> !projectsQueryService.getProjectIds(sourceConfigurationId).collect(toSet()).isEmpty());

        // FIXME: Add organization to project ID
        //  Project ID should have ORG in it (Or only org?)
        // FIXME: null
        val projectId = ProjectId.builder().configurationId(sourceConfigurationId).name("Example-Project").build();
        await("Project found")
                .untilAsserted(() -> assertThat(projectsQueryService.find(projectId)).isPresent());

        val revision = new Revision("main");

        projectsCommandService.registerTrackedRevision(projectId, revision);

        adapterFixture.setupExecution(metadata -> {
            System.out.println(metadata);
        });

        val workflowExecutionId = workflowExecutionCommandService.triggerManualWorkflow(projectId, revision, Paths.get("test-workflow.yml"));

       /*
       JobExecutionId executionId = projectsCommandService.executeManualWorkflow(projectId, "main", new File("example.yaml"));

        await("Execution done").untilAsserted(() ->
                Assertions.assertThat(jobExecutionQueryService.getExecutionDetails(executionId)).isEqualTo(new ExecutionDetailsDto()));
       */
    }

}
