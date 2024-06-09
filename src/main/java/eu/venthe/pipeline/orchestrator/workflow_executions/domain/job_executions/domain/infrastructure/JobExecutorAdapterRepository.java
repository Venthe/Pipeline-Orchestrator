package eu.venthe.pipeline.orchestrator.workflow_executions.domain.job_executions.domain.infrastructure;

import eu.venthe.pipeline.orchestrator.workflow_executions.domain.job_executions.adapters.template.model.AdapterId;
import eu.venthe.pipeline.orchestrator.workflow_executions.domain.job_executions.domain.model.AdapterInstanceAggregate;
import eu.venthe.pipeline.orchestrator.shared_kernel.DomainRepository;

public interface JobExecutorAdapterRepository extends DomainRepository<AdapterInstanceAggregate, AdapterId> {
}
