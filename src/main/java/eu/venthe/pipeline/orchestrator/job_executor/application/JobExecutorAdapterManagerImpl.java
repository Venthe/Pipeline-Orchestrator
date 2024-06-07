package eu.venthe.pipeline.orchestrator.job_executor.application;

import eu.venthe.pipeline.orchestrator.job_executor.adapters.JobExecutorAdapterProvider;
import eu.venthe.pipeline.orchestrator.job_executor.adapters.template.JobExecutorAdapter;
import eu.venthe.pipeline.orchestrator.job_executor.adapters.template.model.AdapterId;
import eu.venthe.pipeline.orchestrator.job_executor.adapters.template.model.AdapterType;
import eu.venthe.pipeline.orchestrator.job_executor.application.runner.RunnerId;
import eu.venthe.pipeline.orchestrator.job_executor.domain.infrastructure.JobExecutorAdapterRepository;
import eu.venthe.pipeline.orchestrator.job_executor.domain.model.AdapterInstanceAggregate;
import eu.venthe.pipeline.orchestrator.job_executor.domain.model.ExecutionId;
import eu.venthe.pipeline.orchestrator.shared_kernel.configuration_properties.SuppliedProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.togglz.core.manager.FeatureManager;
import org.togglz.core.util.NamedFeature;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class JobExecutorAdapterManagerImpl implements JobExecutorAdapterManager, JobExecutorCommandService, JobExecutorQueryService {
    private final JobExecutorAdapterRepository jobExecutorAdapterRepository;
    private final JobExecutorAdapterProvider jobExecutorAdapterProvider;
    private final FeatureManager featureManager;

    @Override
    public AdapterId registerExecutorAdapter(AdapterId adapterId, AdapterType adapterType, SuppliedProperties properties) {

        JobExecutorAdapter.AdapterInstance adapterInstance = jobExecutorAdapterProvider.provide(adapterType, properties);

        jobExecutorAdapterRepository.save(new AdapterInstanceAggregate(adapterId, adapterInstance));

        return adapterId;
    }

    @Override
    public RunnerId registerRunner(AdapterId adapterId,
                                   Map.Entry<String, String>... dimensions) {
        if (featureManager.isActive(new NamedFeature("GENERAL_WIP"))) {
            return new RunnerId("0xDEADBEEF");
        }
        throw new UnsupportedOperationException();
    }

    @Override
    public ExecutionDetailsDto getExecutionDetails(ExecutionId executionId) {
        throw new UnsupportedOperationException();
    }
}
