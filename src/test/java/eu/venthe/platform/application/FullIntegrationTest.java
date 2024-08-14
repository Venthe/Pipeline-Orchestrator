package eu.venthe.platform.application;

import eu.venthe.platform.application.fixtures.MockAdapterFixture;
import eu.venthe.platform.application.fixtures.MockProjectSourceFixture;
import eu.venthe.platform.runner.RunnerManager;
import eu.venthe.platform.runner.model.RegisterRunnerEngineInstanceSpecification;
import eu.venthe.platform.runner.runner_engine.template.model.dimensions.RunnerDimensions;
import eu.venthe.platform.workflow.JobExecutorCallbackService;
import eu.venthe.platform.workflow.WorkflowRunCommandService;
import eu.venthe.platform.workflow.WorkflowRunQueryService;
import eu.venthe.platform.workflow.runs.JobCallbackCallMetadata;
import eu.venthe.platform.workflow.runs._archive._1.model.query.JobExecutionDetailsDto;
import eu.venthe.platform.namespace.application.NamespaceCommandService;
import eu.venthe.platform.namespace.application.model.CreateNamespaceSpecification;
import eu.venthe.platform.source_configuration.application.SourceConfigurationSpecification;
import eu.venthe.platform.project.application.ProjectsCommandService;
import eu.venthe.platform.project.application.ProjectsQueryService;
import eu.venthe.platform.source_configuration.application.ProjectSourcesManager;
import eu.venthe.platform.shared_kernel.project.ProjectId;
import eu.venthe.platform.shared_kernel.project.ProjectStatus;
import eu.venthe.platform.source_configuration.domain.plugins.template.Project;
import eu.venthe.platform.shared_kernel.git.GitRevision;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.assertj.core.api.Assertions;
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
@Slf4j
class FullIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    RunnerManager runnerManager;
    @Autowired
    ProjectSourcesManager projectSourcesManager;
    @Autowired
    ProjectsCommandService projectsCommandService;
    @Autowired
    WorkflowRunCommandService projectWorkflowCommandService;
    @Autowired
    JobExecutorCallbackService callbackService;
    @Autowired
    NamespaceCommandService namespaceCommandService;

    @Autowired
    ProjectsQueryService projectsQueryService;
    @Autowired
    WorkflowRunQueryService workflowRunQueryService;

    @Autowired
    MockProjectSourceFixture projectSourceFixture;
    @Autowired
    MockAdapterFixture adapterFixture;

    @Test
    void name() {
        val projectName = "Example-Project";
        val requestedOrganizationId = "default";
        val sourceType = "test";
        val requestedSourceConfigurationId = "default";
        val engineInstanceId = "default";
        val engineType = "default";

        projectSourceFixture.onInstance(projectSource -> {
            when(projectSource.getProjectIds()).thenReturn(Stream.of(new Project.Id(projectName)));
            when(projectSource.getProject(projectName)).thenReturn(Optional.of(new Project(projectName, ProjectStatus.ACTIVE)));
            when(projectSource.getFile(eq(projectName), eq("main"), eq(Path.of(".mantle", "workflows", "test-workflow.yml"))))
                    .thenReturn(Optional.of("""
                            name: Test
                            on: workflow_dispatch
                            jobs:
                              a:
                                steps:
                                  - command: echo 1
                                  - command: echo 2
                            """.getBytes(StandardCharsets.UTF_8)));
        });

        val organizationSpecification = CreateNamespaceSpecification.builder()
                .organizationId(requestedOrganizationId)
                .build();
        final var organizationId = namespaceCommandService.create(organizationSpecification);

        val sourceSpecification = SourceConfigurationSpecification.builder()
                .organizationId(organizationId)
                .sourceType(sourceType)
                .configurationId(requestedSourceConfigurationId)
                .build();
        val sourceConfigurationId = namespaceCommandService.addSourceConfiguration(sourceSpecification);

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

        val revision = new GitRevision("main");

        projectsCommandService.registerTrackedRevision(projectId, revision);

        adapterFixture.setupExecution(metadata -> {
            System.out.println(metadata);
            var _metadata = new JobCallbackCallMetadata(metadata.projectId(), metadata.workflowRunId(), metadata.executionId(), metadata.runCallbackToken());
            var _context = callbackService.requestContext(_metadata);
            log.info("Context:{}\nMetadata: {}", _context, _metadata);
            callbackService.jobRunStarted(_metadata);
            callbackService.jobRunProgressed(_metadata);
            callbackService.jobRunCompleted(_metadata);
        });

        val workflowExecutionId = projectWorkflowCommandService.triggerWorkflowDispatch(projectId, revision, Paths.get("test-workflow.yml"));

        await("Execution done").untilAsserted(() ->
                Assertions.assertThat(workflowRunQueryService.getExecutionDetails(workflowExecutionId)).isEqualTo(new JobExecutionDetailsDto()));
    }

}
