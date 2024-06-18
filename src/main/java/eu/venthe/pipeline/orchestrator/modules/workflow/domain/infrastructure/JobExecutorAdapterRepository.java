package eu.venthe.pipeline.orchestrator.modules.workflow.domain.infrastructure;

import eu.venthe.pipeline.orchestrator.modules.workflow.domain.job_executions.AdapterInstanceAggregate;
import eu.venthe.pipeline.orchestrator.modules.workflow.domain.job_executions.adapters.template.model.AdapterId;
import eu.venthe.pipeline.orchestrator.shared_kernel.DomainRepository;

public interface JobExecutorAdapterRepository extends DomainRepository<AdapterInstanceAggregate, AdapterId> {
}
