package eu.venthe.pipeline.orchestrator.fixtures;

import eu.venthe.pipeline.orchestrator.modules.automation.runners.runner_engine.template.model.ExecutionCallbackToken;
import eu.venthe.pipeline.orchestrator.modules.automation.runners.runner_engine.template.RunnerEngineDefinition;
import eu.venthe.pipeline.orchestrator.modules.automation.runners.runner_engine.template.model.dimensions.RunnerDimensions;
import eu.venthe.pipeline.orchestrator.modules.automation.runners.runner_engine.template.RunnerEngineInstance;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.model.JobRunId;
import eu.venthe.pipeline.orchestrator.projects.domain.ProjectId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mockito.Mockito;

import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import static org.mockito.ArgumentMatchers.any;

@RequiredArgsConstructor
@Slf4j
public class MockAdapterFixture {
    private final MockRunnerEngineInstance adapterInstance;
    private final TestRunnerEngineDefinition executionAdapter;

    public void setupExecution(Consumer<MockRunnerEngineInstance.Metadata> consumer) {
        Mockito.doAnswer(invocation -> {
            var projectId = invocation.getArgument(0, ProjectId.class);
            var executionId = invocation.getArgument(1, JobRunId.class);
            var systemApiUrl = invocation.getArgument(2, URL.class);
            var callbackToken = invocation.getArgument(3, ExecutionCallbackToken.class);
            var dimensions = invocation.getArgument(4, RunnerDimensions.class);

            try (ExecutorService threadExecutor = Executors.newSingleThreadExecutor()) {
                threadExecutor.execute(() -> {
                    log.info("Executing {} for {}", executionId, projectId);
                    consumer.accept(new MockRunnerEngineInstance.Metadata(projectId, executionId, systemApiUrl, callbackToken, dimensions));
                    log.info("Execution {} complete.", executionId);
                });
            }
            return null;
        }).when(adapterInstance).queueExecution(any(), any(), any(), any(), any());
    }


    public interface TestRunnerEngineDefinition extends RunnerEngineDefinition {
    }

    public interface MockRunnerEngineInstance extends RunnerEngineInstance {
        record Metadata(ProjectId projectId, JobRunId executionId, URL systemApiUrl,
                        ExecutionCallbackToken executionCallbackToken, RunnerDimensions dimensions) {
        }
    }
}
