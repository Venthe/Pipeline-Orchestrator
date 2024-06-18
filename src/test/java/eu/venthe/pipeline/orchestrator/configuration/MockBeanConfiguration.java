package eu.venthe.pipeline.orchestrator.configuration;

import eu.venthe.pipeline.orchestrator.modules.workflow.domain.job_executions.adapters.template.JobExecutorAdapter;
import eu.venthe.pipeline.orchestrator.modules.workflow.domain.job_executions.adapters.template.model.AdapterType;
import eu.venthe.pipeline.orchestrator.modules.workflow.domain.job_executions.model.RunnerDimensions;
import eu.venthe.pipeline.orchestrator.modules.workflow.domain.model.JobExecutionId;
import eu.venthe.pipeline.orchestrator.projects.domain.ProjectId;
import eu.venthe.pipeline.orchestrator.projects.domain.source_configurations.plugins.template.ProjectSourcePlugin;
import eu.venthe.pipeline.orchestrator.projects.domain.source_configurations.plugins.template.model.SourceType;
import lombok.extern.slf4j.Slf4j;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import static org.mockito.ArgumentMatchers.any;

@Slf4j
@TestConfiguration
public class MockBeanConfiguration {
    @Bean
    public MockProjectSource testProjectSourcePluginPluginInstance() {
        return Mockito.mock(MockProjectSource.class);
    }

    @Bean
    public TestProjectSourcePlugin testProjectSourcePlugin(MockProjectSource instance) {
        var mock = Mockito.mock(TestProjectSourcePlugin.class);
        Mockito.when(mock.getSourceType()).thenReturn(new SourceType("test"));
        Mockito.when(mock.instantiate(any())).thenReturn(instance);
        return mock;
    }


    @Bean
    public MockAdapterInstance testJobExecutorAdapterAdapterInstance() {
        return Mockito.mock(MockAdapterInstance.class);
    }

    @Bean
    public TestJobExecutorAdapter testJobExecutorAdapter(MockAdapterInstance adapterInstance) {
        var mock = Mockito.mock(TestJobExecutorAdapter.class);
        Mockito.when(mock.getAdapterType()).thenReturn(new AdapterType("test"));
        Mockito.when(mock.instantiate(any())).thenReturn(adapterInstance);
        return mock;
    }

    public static void setupExecution(MockAdapterInstance mock, Consumer<MockAdapterInstance.Metadata> consumer) {
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
        }).when(mock).queueJobExecution(any(), any(), any(), any(), any());
    }

    public interface TestJobExecutorAdapter extends JobExecutorAdapter {
    }

    public interface MockAdapterInstance extends JobExecutorAdapter.AdapterInstance {
        record Metadata(ProjectId projectId, JobExecutionId executionId, URL systemApiUrl,
                        JobExecutorAdapter.CallbackToken callbackToken, RunnerDimensions dimensions) {
        }
    }

    public interface TestProjectSourcePlugin extends ProjectSourcePlugin {}

    public interface MockProjectSource extends ProjectSourcePlugin.PluginInstance {}
}
