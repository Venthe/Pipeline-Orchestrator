package eu.venthe.pipeline.orchestrator.modules.automation.workflows.execution;

import eu.venthe.pipeline.orchestrator.modules.automation.runners.AdapterInstanceAggregate;
import eu.venthe.pipeline.orchestrator.modules.automation.runners.ExecutionAdapterManager;
import eu.venthe.pipeline.orchestrator.modules.automation.runners.RegisterAdapterSpecification;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.JobExecutionQueryService;
import eu.venthe.pipeline.orchestrator.modules.automation.runners.adapters.template.JobExecutorAdapter;
import eu.venthe.pipeline.orchestrator.modules.automation.runners.adapters.template.model.AdapterId;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.execution.model.RunnerDimensions;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.execution.model.RunnerId;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.model.JobExecutionId;
import eu.venthe.pipeline.orchestrator.utilities.EnvUtil;
import eu.venthe.pipeline.orchestrator.modules.automation.runners.infrastructure.JobExecutorAdapterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.togglz.core.manager.FeatureManager;
import org.togglz.core.util.NamedFeature;

@Component
@RequiredArgsConstructor
public class JobExecutionAdapterManagerImpl implements ExecutionAdapterManager, JobExecutionQueryService {
    private final JobExecutorAdapterRepository repository;
    private final JobExecutorAdapterProvider jobExecutorAdapterProvider;
    private final FeatureManager featureManager;
    private final EnvUtil envUtil;

    @Override
    public AdapterId registerAdapter(RegisterAdapterSpecification specification) {
        JobExecutorAdapter.AdapterInstance adapterInstance = jobExecutorAdapterProvider.provide(specification.adapterType(), specification.properties());

        repository.save(new AdapterInstanceAggregate(specification.adapterId(), adapterInstance));

        return specification.adapterId();
    }

    @Override
    public RunnerId registerRunner(AdapterId adapterId,
                                   RunnerDimensions dimensions) {
        if (!featureManager.isActive(new NamedFeature("GENERAL_WIP"))) {
            throw new UnsupportedOperationException();
        }

        AdapterInstanceAggregate docker = repository.find(adapterId).orElseThrow();
        return docker.registerRunner(dimensions);
    }

    @Override
    public ExecutionDetailsDto getExecutionDetails(JobExecutionId executionId) {
        throw new UnsupportedOperationException();
    }
/*
    @SneakyThrows
    @Override
    public JobExecutionId triggerJobExecution(ProjectId projectId, Dimension... dimensions) {
        if (!featureManager.isActive(new NamedFeature("GENERAL_WIP"))) {
            throw new UnsupportedOperationException();
        }

        AdapterInstanceAggregate docker = repository.find(new AdapterId("default")).orElseThrow();

        JobExecutionId executionId = new JobExecutionId(UUID.randomUUID().toString());
        docker.queueJobExecution(projectId, executionId, envUtil.getServerUrl(), new JobExecutorAdapter.CallbackToken("TEST_TOKEN"));
        return executionId;
    }*/
}
