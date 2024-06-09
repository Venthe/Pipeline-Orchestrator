package eu.venthe.pipeline.orchestrator.workflow_executions.domain.job_executions.application;

import eu.venthe.pipeline.orchestrator.organizations.domain.OrganizationId;
import eu.venthe.pipeline.orchestrator.shared_kernel.configuration_properties.SuppliedProperties;
import eu.venthe.pipeline.orchestrator.workflow_executions.domain.job_executions.adapters.template.model.AdapterId;
import eu.venthe.pipeline.orchestrator.workflow_executions.domain.job_executions.adapters.template.model.AdapterType;
import eu.venthe.pipeline.orchestrator.workflow_executions.domain.job_executions.application.runner.RunnerDimensions;
import eu.venthe.pipeline.orchestrator.workflow_executions.domain.job_executions.application.runner.RunnerId;

public interface ExecutorManager {

    AdapterId registerAdapter(OrganizationId organizationId, AdapterId adapterId, AdapterType adapterType, SuppliedProperties properties);

    AdapterId registerAdapter(OrganizationId organizationId, AdapterId adapterId, AdapterType adapterType);

    RunnerId registerRunner(AdapterId adapterId,
                            RunnerDimensions dimensions);
}
