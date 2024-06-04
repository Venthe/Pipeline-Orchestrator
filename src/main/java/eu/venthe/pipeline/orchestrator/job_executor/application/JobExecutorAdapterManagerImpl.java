package eu.venthe.pipeline.orchestrator.job_executor.application;

import eu.venthe.pipeline.orchestrator.job_executor.adapters.JobExecutorAdapterProvider;
import eu.venthe.pipeline.orchestrator.job_executor.adapters.template.JobExecutorAdapter;
import eu.venthe.pipeline.orchestrator.job_executor.adapters.template.model.AdapterId;
import eu.venthe.pipeline.orchestrator.job_executor.adapters.template.model.AdapterType;
import eu.venthe.pipeline.orchestrator.job_executor.application.runner.ContainerId;
import eu.venthe.pipeline.orchestrator.job_executor.application.runner.JobExecutionRunner;
import eu.venthe.pipeline.orchestrator.job_executor.application.runner.RunnerId;
import eu.venthe.pipeline.orchestrator.job_executor.domain.infrastructure.JobExecutorAdapterRepository;
import eu.venthe.pipeline.orchestrator.shared_kernel.configuration_properties.SuppliedProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class JobExecutorAdapterManagerImpl implements JobExecutorAdapterManager, JobExecutorCommandService {
    private final JobExecutorAdapterRepository jobExecutorAdapterRepository;
    private final JobExecutorAdapterProvider jobExecutorAdapterProvider;

    @Override
    public AdapterId registerExecutorAdapter(AdapterId adapterId, AdapterType adapterType, SuppliedProperties properties) {
        JobExecutorAdapter.AdapterInstance adapterInstance = jobExecutorAdapterProvider.provide(adapterType, properties);

        jobExecutorAdapterRepository.save(adapterInstance);

        return adapterId;
    }

    @Override
    public RunnerId registerRunnerForAdapter(AdapterId adapterId,
                                             ContainerId containerTag,
                                             JobExecutionRunner.OperatingSystem operatingSystem,
                                             JobExecutionRunner.Architecture architecture,
                                             Map.Entry<String, String>... dimensions) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setDefault(AdapterId executorId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setDefault(RunnerId runnerId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void triggerJobExecution(AdapterId adapterId,
                                    ContainerId containerId,
                                    Map.Entry<String, String>... dimensions) {
        throw new UnsupportedOperationException();
    }
}
