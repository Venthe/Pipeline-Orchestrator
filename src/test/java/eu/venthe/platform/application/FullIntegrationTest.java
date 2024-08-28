package eu.venthe.platform.application;

import eu.venthe.platform.application.fixtures.MockAdapterFixture;
import eu.venthe.platform.application.fixtures.MockRepositorySourceFixture;
import eu.venthe.platform.organization.application.OrganizationCommandService;
import eu.venthe.platform.organization.application.model.CreateOrganizationSpecification;
import eu.venthe.platform.repository.application.RepositoryCommandService;
import eu.venthe.platform.repository.application.RepositoryQueryService;
import eu.venthe.platform.repository.domain.RepositoryName;
import eu.venthe.platform.runner.RunnerManager;
import eu.venthe.platform.runner.model.RegisterRunnerEngineInstanceSpecification;
import eu.venthe.platform.runner.runner_engine.template.model.dimensions.RunnerDimensions;
import eu.venthe.platform.shared_kernel.git.RevisionShortName;
import eu.venthe.platform.shared_kernel.io.File;
import eu.venthe.platform.shared_kernel.project.RepositoryStatus;
import eu.venthe.platform.source_configuration.application.SourceConfigurationSpecification;
import eu.venthe.platform.source_configuration.domain.plugins.template.Repository;
import eu.venthe.platform.source_configuration.domain.plugins.template.SourceRepositoryName;
import eu.venthe.platform.workflow.JobExecutorCallbackService;
import eu.venthe.platform.workflow.WorkflowRunCommandService;
import eu.venthe.platform.workflow.WorkflowRunQueryService;
import eu.venthe.platform.workflow.runs.JobCallbackCallMetadata;
import eu.venthe.platform.workflow.runs.WorkflowRunStatus;
import eu.venthe.platform.workflow.runs._archive._1.model.query.WorkflowExecutionDto;
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
import java.time.ZonedDateTime;
import java.util.Map;
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
    RepositoryCommandService repositorysCommandService;
    @Autowired
    WorkflowRunCommandService repositoryWorkflowCommandService;
    @Autowired
    JobExecutorCallbackService callbackService;
    @Autowired
    OrganizationCommandService organizationCommandService;

    @Autowired
    RepositoryQueryService repositorysQueryService;
    @Autowired
    WorkflowRunQueryService workflowRunQueryService;

    @Autowired
    MockRepositorySourceFixture repositorySourceFixture;
    @Autowired
    MockAdapterFixture adapterFixture;

    @Test
    void name() {
        val repositoryName = new SourceRepositoryName("Example-Repository");
        val organizationName = "default";
        val sourceType = "test";
        val engineInstanceId = "default";
        val engineType = "default";
        val revision = new SimpleRevision("main");

        repositorySourceFixture.onInstance(repositorySource -> {
            when(repositorySource.getRepositoryNameentifiers()).thenReturn(Stream.of(repositoryName));
            when(repositorySource.getRepository(repositoryName)).thenReturn(Optional.of(new Repository(repositoryName, RepositoryStatus.ACTIVE)));
            when(repositorySource.getFile(eq(repositoryName), eq(revision), eq(Path.of(".mantle", "workflows", "test-workflow.yml"))))
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

        val organizationSpecification = CreateOrganizationSpecification.builder()
                .organizationName(organizationName)
                .source(sourceSpecification)
                .build();
        val createdOrganizationName = organizationCommandService.register(organizationSpecification);

        val adapterSpecification = RegisterRunnerEngineInstanceSpecification.builder()
                .organizationName(createdOrganizationName)
                .runnerEngineInstanceId(engineInstanceId)
                .runnerEngineType(engineType)
                .build();
        val defaultExecutor = runnerManager.registerRunnerEngineInstance(adapterSpecification);

        val defaultRunner = runnerManager.registerRunner(defaultExecutor, RunnerDimensions.none());

        // At this point, auto synchronization should happen. Let's wait for it.
        await("Synchronization done")
                .until(() -> !repositorysQueryService.getRepositoryNames(createdOrganizationName).collect(toSet()).isEmpty());

        val repositoryId = new RepositoryName(createdOrganizationName, repositoryName);
        await("Repository found")
                .untilAsserted(() -> assertThat(repositorysQueryService.find(repositoryId)).isPresent());

        adapterFixture.setupExecution((context, dimensions) -> {
            var _metadata = new JobCallbackCallMetadata(context.jobRunRequestContext().system().workflowRunId(), context.jobRunRequestContext().system().jobRunId(), context.runCallbackToken());
            log.info("Metadata: {}, Dimensions: {}", context, dimensions);
            callbackService.jobRunStarted(_metadata, ZonedDateTime.now());
            callbackService.jobRunProgressed(_metadata, Map.of());
            callbackService.jobRunCompleted(_metadata, ZonedDateTime.now(), Map.of());
        });

        val workflowRunId = repositoryWorkflowCommandService.triggerWorkflowDispatch(repositoryId, revision, Paths.get("test-workflow.yml"));

        await("Execution done").untilAsserted(() ->
                Assertions.assertThat(workflowRunQueryService.getExecutionDetails(workflowRunId)).isPresent().contains(new WorkflowExecutionDto(workflowRunId, WorkflowRunStatus.SUCCESS)));
    }

}
