package eu.venthe.pipeline.orchestrator.modules.workflow.application.impl;

import eu.venthe.pipeline.orchestrator.modules.workflow.application.ExecutionAdapterManager;
import eu.venthe.pipeline.orchestrator.modules.workflow.application.JobExecutionQueryService;
import eu.venthe.pipeline.orchestrator.modules.workflow.application.dto.ExecutionDetailsDto;
import eu.venthe.pipeline.orchestrator.modules.workflow.application.dto.RegisterAdapterSpecification;
import eu.venthe.pipeline.orchestrator.modules.workflow.domain.job_executions.AdapterInstanceAggregate;
import eu.venthe.pipeline.orchestrator.modules.workflow.domain.job_executions.JobExecutorAdapterProvider;
import eu.venthe.pipeline.orchestrator.modules.workflow.domain.job_executions.adapters.template.JobExecutorAdapter;
import eu.venthe.pipeline.orchestrator.modules.workflow.domain.job_executions.adapters.template.model.AdapterId;
import eu.venthe.pipeline.orchestrator.modules.workflow.domain.job_executions.adapters.template.model.AdapterType;
import eu.venthe.pipeline.orchestrator.modules.workflow.domain.job_executions.model.RunnerDimensions;
import eu.venthe.pipeline.orchestrator.modules.workflow.domain.job_executions.model.RunnerId;
import eu.venthe.pipeline.orchestrator.modules.workflow.domain.model.JobExecutionId;
import eu.venthe.pipeline.orchestrator.organizations.domain.OrganizationId;
import eu.venthe.pipeline.orchestrator.shared_kernel.configuration_properties.SuppliedProperties;
import eu.venthe.pipeline.orchestrator.utilities.EnvUtil;
import eu.venthe.pipeline.orchestrator.modules.workflow.domain.infrastructure.JobExecutorAdapterRepository;
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
