package eu.venthe.pipeline.orchestrator.job_executor.application;

import eu.venthe.pipeline.orchestrator.job_executor.adapters.JobExecutorAdapterProvider;
import eu.venthe.pipeline.orchestrator.job_executor.adapters.template.JobExecutorAdapter;
import eu.venthe.pipeline.orchestrator.job_executor.adapters.template.model.AdapterId;
import eu.venthe.pipeline.orchestrator.job_executor.adapters.template.model.AdapterType;
import eu.venthe.pipeline.orchestrator.job_executor.domain.infrastructure.JobExecutorAdapterRepository;
import eu.venthe.pipeline.orchestrator.shared_kernel.configuration_properties.SuppliedProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JobExecutorAdapterManagerImpl implements JobExecutorAdapterManager {
    private final JobExecutorAdapterRepository jobExecutorAdapterRepository;
    private final JobExecutorAdapterProvider jobExecutorAdapterProvider;

    @Override
    public AdapterId registerGlobal(AdapterId adapterId, AdapterType adapterType, SuppliedProperties properties) {
        JobExecutorAdapter.AdapterInstance adapterInstance = jobExecutorAdapterProvider.provide(adapterType, properties);
        throw new UnsupportedOperationException();
    }

    @Override
    public String registerRunner(AdapterId executorId, String s) {
        throw new UnsupportedOperationException();
    }
}
