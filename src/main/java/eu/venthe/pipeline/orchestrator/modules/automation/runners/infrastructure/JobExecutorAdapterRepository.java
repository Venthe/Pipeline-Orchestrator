package eu.venthe.pipeline.orchestrator.modules.automation.runners.infrastructure;

import eu.venthe.pipeline.orchestrator.modules.automation.runners.impl.model.RunnerEngineInstanceAggregate;
import eu.venthe.pipeline.orchestrator.modules.automation.runners.runner_engine.template.model.RunnerEngineInstanceId;
import eu.venthe.pipeline.orchestrator.shared_kernel.DomainRepository;

public interface JobExecutorAdapterRepository extends DomainRepository<RunnerEngineInstanceAggregate, RunnerEngineInstanceId> {
}
