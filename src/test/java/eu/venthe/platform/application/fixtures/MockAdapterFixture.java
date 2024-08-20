package eu.venthe.platform.application.fixtures;

import eu.venthe.platform.project.domain.ProjectId;
import eu.venthe.platform.runner.runner_engine.template.RunnerEngineDefinition;
import eu.venthe.platform.runner.runner_engine.template.RunnerEngineInstance;
import eu.venthe.platform.runner.runner_engine.template.model.RunCallbackToken;
import eu.venthe.platform.runner.runner_engine.template.model.RunnerContext;
import eu.venthe.platform.runner.runner_engine.template.model.dimensions.RunnerDimensions;
import eu.venthe.platform.workflow.model.JobRunId;
import eu.venthe.platform.workflow.runs.WorkflowRunId;
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

    public void setupExecution(ContextConsumer consumer) {
        Mockito.doAnswer(invocation -> {
            var context = invocation.getArgument(0, RunnerContext.class);
            var dimensions = invocation.getArgument(1, RunnerDimensions.class);

            try (ExecutorService threadExecutor = Executors.newSingleThreadExecutor()) {
                threadExecutor.execute(() -> {
                    log.info("Executing {} for {}", context, dimensions);
                    consumer.accept(context, dimensions);
                    log.info("Execution {} complete.", (Object) context);
                });
            }
            return null;
        }).when(adapterInstance).queueExecution(any(), any());
    }


    public interface TestRunnerEngineDefinition extends RunnerEngineDefinition {
    }

    public interface MockRunnerEngineInstance extends RunnerEngineInstance {
    }

    @FunctionalInterface
    public interface ContextConsumer {
        void accept(RunnerContext context, RunnerDimensions dimensions);
    }
}
