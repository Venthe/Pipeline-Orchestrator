package eu.venthe.pipeline.orchestrator.job_executor.application;

import eu.venthe.pipeline.orchestrator.job_executor.adapters.JobExecutorAdapterProvider;
import eu.venthe.pipeline.orchestrator.job_executor.adapters.template.JobExecutorAdapter;
import eu.venthe.pipeline.orchestrator.job_executor.adapters.template.model.AdapterId;
import eu.venthe.pipeline.orchestrator.job_executor.adapters.template.model.AdapterType;
import eu.venthe.pipeline.orchestrator.job_executor.application.runner.RunnerDimensions;
import eu.venthe.pipeline.orchestrator.job_executor.application.runner.RunnerId;
import eu.venthe.pipeline.orchestrator.job_executor.domain.infrastructure.JobExecutorAdapterRepository;
import eu.venthe.pipeline.orchestrator.job_executor.domain.model.AdapterInstanceAggregate;
import eu.venthe.pipeline.orchestrator.job_executor.domain.model.ExecutionId;
import eu.venthe.pipeline.orchestrator.projects.projects.domain.model.ProjectId;
import eu.venthe.pipeline.orchestrator.shared_kernel.configuration_properties.SuppliedProperties;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.togglz.core.manager.FeatureManager;
import org.togglz.core.util.NamedFeature;

import java.net.URL;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JobExecutorAdapterManagerImpl implements JobExecutorAdapterManager, JobExecutorCommandService, JobExecutorQueryService {
    private final JobExecutorAdapterRepository repository;
    private final JobExecutorAdapterProvider jobExecutorAdapterProvider;
    private final FeatureManager featureManager;

    @Override
    public AdapterId registerExecutorAdapter(AdapterId adapterId, AdapterType adapterType, SuppliedProperties properties) {

        JobExecutorAdapter.AdapterInstance adapterInstance = jobExecutorAdapterProvider.provide(adapterType, properties);

        repository.save(new AdapterInstanceAggregate(adapterId, adapterInstance));

        return adapterId;
    }

    @Override
    public RunnerId registerRunner(AdapterId adapterId,
                                   Map.Entry<String, String>... dimensions) {
        if (!featureManager.isActive(new NamedFeature("GENERAL_WIP"))) {
            throw new UnsupportedOperationException();
        }

        AdapterInstanceAggregate docker = repository.find(new AdapterId("docker-test")).orElseThrow();
        return docker.registerRunner(Arrays.stream(dimensions).map(RunnerDimensions.Dimension::new).toArray(RunnerDimensions.Dimension[]::new));
    }

    @Override
    public ExecutionDetailsDto getExecutionDetails(ExecutionId executionId) {
        throw new UnsupportedOperationException();
    }

    @SneakyThrows
    @Override
    public ExecutionId triggerJobExecution(ProjectId projectId, RunnerDimensions.Dimension... dimensions) {
        if (!featureManager.isActive(new NamedFeature("GENERAL_WIP"))) {
            throw new UnsupportedOperationException();
        }

        AdapterInstanceAggregate docker = repository.find(new AdapterId("docker-test")).orElseThrow();

        ExecutionId executionId = new ExecutionId(UUID.randomUUID().toString());
        docker.queueJobExecution(projectId, executionId, new URL("http://localhost:8081"), new JobExecutorAdapter.CallbackToken("TEST_TOKEN"));
        return executionId;
    }
}
