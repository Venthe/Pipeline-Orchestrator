package eu.venthe.pipeline.orchestrator;

import eu.venthe.pipeline.orchestrator.fixtures.MockAdapterFixture;
import eu.venthe.pipeline.orchestrator.fixtures.MockProjectSourceFixture;
import eu.venthe.pipeline.orchestrator.modules.automation.runners.RunnerManager;
import eu.venthe.pipeline.orchestrator.modules.automation.runners.model.RegisterRunnerEngineInstanceSpecification;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.JobExecutorCallbackService;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.ProjectWorkflowCommandService;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.WorkflowExecutionQueryService;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.runs._archive._1.model.query.JobExecutionDetailsDto;
import eu.venthe.pipeline.orchestrator.modules.automation.runners.runner_engine.template.model.dimensions.RunnerDimensions;
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
import org.assertj.core.api.Assertions;
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
    RunnerManager runnerManager;
    @Autowired
    ProjectSourcesManager projectSourcesManager;
    @Autowired
    ProjectsCommandService projectsCommandService;
    @Autowired
    ProjectWorkflowCommandService projectWorkflowCommandService;
    @Autowired
    JobExecutorCallbackService callbackService;
    @Autowired
    OrganizationCommandService organizationCommandService;

    @Autowired
    ProjectsQueryService projectsQueryService;
    @Autowired
    WorkflowExecutionQueryService workflowExecutionQueryService;

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
        val engineInstanceId = "default";
        val engineType = "default";

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

        val adapterSpecification = RegisterRunnerEngineInstanceSpecification.builder()
                .organizationId(organizationId)
                .runnerEngineInstanceId(engineInstanceId)
                .runnerEngineType(engineType)
                .build();
        val defaultExecutor = runnerManager.registerRunnerEngineInstance(adapterSpecification);

        val defaultRunner = runnerManager.registerRunner(defaultExecutor, RunnerDimensions.none());

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

        val workflowExecutionId = projectWorkflowCommandService.triggerWorkflowDispatch(projectId, revision, Paths.get("test-workflow.yml"));

        await("Execution done").untilAsserted(() ->
                Assertions.assertThat(workflowExecutionQueryService.getExecutionDetails(workflowExecutionId)).isEqualTo(new JobExecutionDetailsDto()));
    }

}
