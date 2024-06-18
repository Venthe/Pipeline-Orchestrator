package eu.venthe.pipeline.orchestrator.modules.workflow.services.impl;

import eu.venthe.pipeline.orchestrator.modules.workflow.workflow_executions.application.ExecutorManager;
import eu.venthe.pipeline.orchestrator.modules.workflow.workflow_executions.application.JobExecutionQueryService;
import eu.venthe.pipeline.orchestrator.modules.workflow.services.dto.ExecutionDetailsDto;
import eu.venthe.pipeline.orchestrator.modules.workflow.domain.job_executions.adapters.AdapterInstanceAggregate;
import eu.venthe.pipeline.orchestrator.modules.workflow.domain.job_executions.adapters.JobExecutorAdapterProvider;
import eu.venthe.pipeline.orchestrator.modules.workflow.domain.job_executions.adapters.template.JobExecutorAdapter;
import eu.venthe.pipeline.orchestrator.modules.workflow.domain.job_executions.adapters.template.model.AdapterId;
import eu.venthe.pipeline.orchestrator.modules.workflow.domain.job_executions.adapters.template.model.AdapterType;
import eu.venthe.pipeline.orchestrator.modules.workflow.domain.job_executions.model.Dimension;
import eu.venthe.pipeline.orchestrator.modules.workflow.domain.job_executions.model.RunnerDimensions;
import eu.venthe.pipeline.orchestrator.modules.workflow.domain.job_executions.model.RunnerId;
import eu.venthe.pipeline.orchestrator.modules.workflow.domain.model.JobExecutionId;
import eu.venthe.pipeline.orchestrator.organizations.domain.OrganizationId;
import eu.venthe.pipeline.orchestrator.organizations.domain.projects.ProjectId;
import eu.venthe.pipeline.orchestrator.shared_kernel.configuration_properties.SuppliedProperties;
import eu.venthe.pipeline.orchestrator.utilities.EnvUtil;
import eu.venthe.pipeline.orchestrator.workflow_executions.application.JobExecutorCommandService;
import eu.venthe.pipeline.orchestrator.modules.workflow.domain.infrastructure.JobExecutorAdapterRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.togglz.core.manager.FeatureManager;
import org.togglz.core.util.NamedFeature;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JobExecutionAdapterManagerImpl implements ExecutorManager, JobExecutorCommandService, JobExecutionQueryService {
    private final JobExecutorAdapterRepository repository;
    private final JobExecutorAdapterProvider jobExecutorAdapterProvider;
    private final FeatureManager featureManager;
    private final EnvUtil envUtil;

    @Override
    public AdapterId registerAdapter(OrganizationId organizationId, AdapterId adapterId, AdapterType adapterType, SuppliedProperties properties) {

        JobExecutorAdapter.AdapterInstance adapterInstance = jobExecutorAdapterProvider.provide(adapterType, properties);

        repository.save(new AdapterInstanceAggregate(adapterId, adapterInstance));

        return adapterId;
    }

    @Override
    public AdapterId registerAdapter(OrganizationId organizationId, AdapterId adapterId, AdapterType adapterType) {
        return registerAdapter(organizationId, adapterId, adapterType, SuppliedProperties.none());
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
    }
}
