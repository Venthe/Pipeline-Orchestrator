package eu.venthe.pipeline.orchestrator.job_executor.application;

import eu.venthe.pipeline.orchestrator.job_executor.adapters.template.model.AdapterId;
import eu.venthe.pipeline.orchestrator.job_executor.adapters.template.model.AdapterType;
import eu.venthe.pipeline.orchestrator.job_executor.domain.infrastructure.JobExecutorRepository;
import eu.venthe.pipeline.orchestrator.shared_kernel.configuration_properties.SuppliedProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JobExecutorAdapterManagerImpl implements JobExecutorAdapterManager {
    private final JobExecutorRepository jobExecutorRepository;

    @Override
    public AdapterId registerGlobal(AdapterId adapterId, AdapterType adapterType, SuppliedProperties properties) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String registerRunner(AdapterId executorId, String s) {
        throw new UnsupportedOperationException();
    }
}
