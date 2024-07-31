package eu.venthe.pipeline.orchestrator.modules.automation.runners.infrastructure;

import eu.venthe.pipeline.orchestrator.modules.automation.runners.impl.model.AdapterInstanceAggregate;
import eu.venthe.pipeline.orchestrator.modules.automation.runners.runner_engine.template.model.RunnerImplementationId;
import eu.venthe.pipeline.orchestrator.shared_kernel.DomainRepository;

public interface JobExecutorAdapterRepository extends DomainRepository<AdapterInstanceAggregate, RunnerImplementationId> {
}
