package eu.venthe.pipeline.orchestrator.configuration;

import eu.venthe.pipeline.orchestrator.workflow_executions.domain.job_executions.adapters.template.JobExecutorAdapter;
import eu.venthe.pipeline.orchestrator.workflow_executions.domain.job_executions.adapters.template.model.AdapterType;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import static org.mockito.ArgumentMatchers.any;

@TestConfiguration
public class TestJobExecutorConfiguration {
    @Bean
    public TestJobExecutorAdapterAdapterInstance testJobExecutorAdapterAdapterInstance() {
        return Mockito.mock(TestJobExecutorAdapterAdapterInstance.class);
    }

    @Bean
    public TestJobExecutorAdapter testJobExecutorAdapter(TestJobExecutorAdapterAdapterInstance adapterInstance) {
        var mock = Mockito.mock(TestJobExecutorAdapter.class);
        Mockito.when(mock.getAdapterType()).thenReturn(new AdapterType("test"));
        Mockito.when(mock.instantiate(any())).thenReturn(adapterInstance);
        return mock;
    }

    public interface TestJobExecutorAdapter extends JobExecutorAdapter {}

    public interface TestJobExecutorAdapterAdapterInstance extends JobExecutorAdapter.AdapterInstance {}
}
