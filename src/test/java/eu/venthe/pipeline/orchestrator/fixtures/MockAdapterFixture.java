package eu.venthe.pipeline.orchestrator.fixtures;

import eu.venthe.pipeline.orchestrator.modules.automation.runners.adapters.template.JobExecutorAdapter;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.execution.model.RunnerDimensions;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.model.JobExecutionId;
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
    private final MockAdapterInstance adapterInstance;
    private final TestJobExecutorAdapter executionAdapter;

    public void setupExecution(Consumer<MockAdapterInstance.Metadata> consumer) {
        Mockito.doAnswer(invocation -> {
            var projectId = invocation.getArgument(0, ProjectId.class);
            var executionId = invocation.getArgument(1, JobExecutionId.class);
            var systemApiUrl = invocation.getArgument(2, URL.class);
            var callbackToken = invocation.getArgument(3, JobExecutorAdapter.CallbackToken.class);
            var dimensions = invocation.getArgument(4, RunnerDimensions.class);

            try (ExecutorService threadExecutor = Executors.newSingleThreadExecutor()) {
                threadExecutor.execute(() -> {
                    log.info("Executing {} for {}", executionId, projectId);
                    consumer.accept(new MockAdapterInstance.Metadata(projectId, executionId, systemApiUrl, callbackToken, dimensions));
                    log.info("Execution {} complete.", executionId);
                });
            }
            return null;
        }).when(adapterInstance).queueJobExecution(any(), any(), any(), any(), any());
    }


    public interface TestJobExecutorAdapter extends JobExecutorAdapter {
    }

    public interface MockAdapterInstance extends JobExecutorAdapter.AdapterInstance {
        record Metadata(ProjectId projectId, JobExecutionId executionId, URL systemApiUrl,
                        JobExecutorAdapter.CallbackToken callbackToken, RunnerDimensions dimensions) {
        }
    }
}
