package eu.venthe.pipeline.orchestrator.modules.workflow.application;

import eu.venthe.pipeline.orchestrator.modules.workflow.domain.job_executions.adapters.template.model.AdapterId;
import eu.venthe.pipeline.orchestrator.organizations.domain.OrganizationId;
import eu.venthe.pipeline.orchestrator.shared_kernel.configuration_properties.SuppliedProperties;
import eu.venthe.pipeline.orchestrator.modules.workflow.domain.job_executions.adapters.template.model.AdapterType;
import eu.venthe.pipeline.orchestrator.modules.workflow.domain.job_executions.model.RunnerDimensions;
import eu.venthe.pipeline.orchestrator.modules.workflow.domain.job_executions.model.RunnerId;

public interface ExecutionAdapterManager {

    AdapterId registerAdapter(OrganizationId organizationId, AdapterId adapterId, AdapterType adapterType, SuppliedProperties properties);

    AdapterId registerAdapter(OrganizationId organizationId, AdapterId adapterId, AdapterType adapterType);

    RunnerId registerRunner(AdapterId adapterId,
                            RunnerDimensions dimensions);
}
