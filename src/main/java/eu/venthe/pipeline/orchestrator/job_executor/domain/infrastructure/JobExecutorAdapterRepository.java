package eu.venthe.pipeline.orchestrator.job_executor.domain.infrastructure;

import eu.venthe.pipeline.orchestrator.job_executor.adapters.template.model.AdapterId;
import eu.venthe.pipeline.orchestrator.job_executor.domain.model.AdapterInstanceAggregate;
import eu.venthe.pipeline.orchestrator.shared_kernel.DomainRepository;

public interface JobExecutorAdapterRepository extends DomainRepository<AdapterInstanceAggregate, AdapterId> {
}
