package eu.venthe.platform.application;

import eu.venthe.platform.application.fixtures.MockAdapterFixture;
import eu.venthe.platform.application.fixtures.MockProjectSourceFixture;
import eu.venthe.platform.namespace.application.NamespaceCommandService;
import eu.venthe.platform.namespace.application.model.CreateNamespaceSpecification;
import eu.venthe.platform.project.application.ProjectsCommandService;
import eu.venthe.platform.project.application.ProjectsQueryService;
import eu.venthe.platform.project.domain.ProjectId;
import eu.venthe.platform.runner.RunnerManager;
import eu.venthe.platform.runner.model.RegisterRunnerEngineInstanceSpecification;
import eu.venthe.platform.runner.runner_engine.template.model.dimensions.RunnerDimensions;
import eu.venthe.platform.shared_kernel.git.SimpleRevision;
import eu.venthe.platform.shared_kernel.io.File;
import eu.venthe.platform.shared_kernel.project.ProjectStatus;
import eu.venthe.platform.source_configuration.application.SourceConfigurationSpecification;
import eu.venthe.platform.source_configuration.domain.plugins.template.Project;
import eu.venthe.platform.source_configuration.domain.plugins.template.SourceProjectId;
import eu.venthe.platform.workflow.JobExecutorCallbackService;
import eu.venthe.platform.workflow.WorkflowRunCommandService;
import eu.venthe.platform.workflow.WorkflowRunQueryService;
import eu.venthe.platform.workflow.runs.JobCallbackCallMetadata;
import eu.venthe.platform.workflow.runs._archive._1.model.query.JobExecutionDetailsDto;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;

import java.io.ByteArrayInputStream;
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
        val projectName = new SourceProjectId("Example-Project");
        val namespaceName = "default";
        val sourceType = "test";
        val engineInstanceId = "default";
        val engineType = "default";
        val revision = new SimpleRevision("main");

        projectSourceFixture.onInstance(projectSource -> {
            when(projectSource.getProjectIdentifiers()).thenReturn(Stream.of(projectName));
            when(projectSource.getProject(projectName)).thenReturn(Optional.of(new Project(projectName, ProjectStatus.ACTIVE)));
            when(projectSource.getFile(eq(projectName), eq(revision), eq(Path.of(".mantle", "workflows", "test-workflow.yml"))))
                    .thenReturn(Optional.of(new File(new ByteArrayInputStream("""
                            name: Test
                            on: workflow_dispatch
                            jobs:
                              a:
                                steps:
                                  - run: echo 1
                                  - run: echo 2
                            """.getBytes(StandardCharsets.UTF_8)), null)));
        });

        val sourceSpecification = SourceConfigurationSpecification.builder()
                .sourceType(sourceType)
                .build();

        val organizationSpecification = CreateNamespaceSpecification.builder()
                .namespaceName(namespaceName)
                .source(sourceSpecification)
                .build();
        val createdNamespaceName = namespaceCommandService.register(organizationSpecification);

        val adapterSpecification = RegisterRunnerEngineInstanceSpecification.builder()
                .namespaceName(createdNamespaceName)
                .runnerEngineInstanceId(engineInstanceId)
                .runnerEngineType(engineType)
                .build();
        val defaultExecutor = runnerManager.registerRunnerEngineInstance(adapterSpecification);

        val defaultRunner = runnerManager.registerRunner(defaultExecutor, RunnerDimensions.none());

        // At this point, auto synchronization should happen. Let's wait for it.
        await("Synchronization done")
                .until(() -> !projectsQueryService.getProjectIds(createdNamespaceName).collect(toSet()).isEmpty());

        val projectId = new ProjectId(createdNamespaceName, projectName);
        await("Project found")
                .untilAsserted(() -> assertThat(projectsQueryService.find(projectId)).isPresent());

        adapterFixture.setupExecution(metadata -> {
            var _metadata = new JobCallbackCallMetadata(null, null, null, metadata.context().runCallbackToken());
            log.info("Metadata: {}", metadata);
            callbackService.jobRunStarted(_metadata);
            callbackService.jobRunProgressed(_metadata);
            callbackService.jobRunCompleted(_metadata);
        });

        val workflowExecutionId = projectWorkflowCommandService.triggerWorkflowDispatch(projectId, revision, Paths.get("test-workflow.yml"));

        await("Execution done").untilAsserted(() ->
                Assertions.assertThat(workflowRunQueryService.getExecutionDetails(workflowExecutionId)).isEqualTo(new JobExecutionDetailsDto()));
    }

}
