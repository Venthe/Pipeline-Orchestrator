package eu.venthe.pipeline.orchestrator.modules.automation.runners.infrastructure;

import eu.venthe.pipeline.orchestrator.modules.automation.runners.AdapterInstanceAggregate;
import eu.venthe.pipeline.orchestrator.modules.automation.runners.adapters.template.model.AdapterId;
import eu.venthe.pipeline.orchestrator.shared_kernel.DomainRepository;

public interface JobExecutorAdapterRepository extends DomainRepository<AdapterInstanceAggregate, AdapterId> {
}
