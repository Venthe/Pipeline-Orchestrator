package eu.venthe.pipeline.orchestrator.workflow_executions.domain.job_executions.application;

import eu.venthe.pipeline.orchestrator.workflow_executions.domain.job_executions.adapters.JobExecutorAdapterProvider;
import eu.venthe.pipeline.orchestrator.workflow_executions.domain.job_executions.adapters.template.JobExecutorAdapter;
import eu.venthe.pipeline.orchestrator.workflow_executions.domain.job_executions.adapters.template.model.AdapterId;
import eu.venthe.pipeline.orchestrator.workflow_executions.domain.job_executions.adapters.template.model.AdapterType;
import eu.venthe.pipeline.orchestrator.workflow_executions.domain.job_executions.application.runner.Dimension;
import eu.venthe.pipeline.orchestrator.workflow_executions.domain.job_executions.application.runner.RunnerId;
import eu.venthe.pipeline.orchestrator.workflow_executions.domain.job_executions.domain.model.AdapterInstanceAggregate;
import eu.venthe.pipeline.orchestrator.workflow_executions.domain.job_executions.domain.model.ExecutionId;
import eu.venthe.pipeline.orchestrator.workflow_executions.domain.job_executions.domain.infrastructure.JobExecutorAdapterRepository;
import eu.venthe.pipeline.orchestrator.projects.domain.model.ProjectId;
import eu.venthe.pipeline.orchestrator.shared_kernel.configuration_properties.SuppliedProperties;
import eu.venthe.pipeline.orchestrator.utilities.EnvUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.togglz.core.manager.FeatureManager;
import org.togglz.core.util.NamedFeature;

import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JobExecutorAdapterManagerImpl implements ExecutorManager, JobExecutorCommandService, JobExecutorQueryService {
    private final JobExecutorAdapterRepository repository;
    private final JobExecutorAdapterProvider jobExecutorAdapterProvider;
    private final FeatureManager featureManager;
    private final EnvUtil envUtil;

    @Override
    public AdapterId registerAdapter(AdapterId adapterId, AdapterType adapterType, SuppliedProperties properties) {

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
        return docker.registerRunner(Arrays.stream(dimensions).map(Dimension::new).toArray(Dimension[]::new));
    }

    @Override
    public ExecutionDetailsDto getExecutionDetails(ExecutionId executionId) {
        throw new UnsupportedOperationException();
    }

    @SneakyThrows
    @Override
    public ExecutionId triggerJobExecution(ProjectId projectId, Dimension... dimensions) {
        if (!featureManager.isActive(new NamedFeature("GENERAL_WIP"))) {
            throw new UnsupportedOperationException();
        }

        AdapterInstanceAggregate docker = repository.find(new AdapterId("docker-test")).orElseThrow();

        ExecutionId executionId = new ExecutionId(UUID.randomUUID().toString());
        docker.queueJobExecution(projectId, executionId, envUtil.getServerUrl(), new JobExecutorAdapter.CallbackToken("TEST_TOKEN"));
        return executionId;
    }
}
