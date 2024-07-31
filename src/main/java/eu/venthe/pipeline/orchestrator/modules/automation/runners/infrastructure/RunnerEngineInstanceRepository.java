package eu.venthe.pipeline.orchestrator.modules.automation.runners.infrastructure;

import eu.venthe.pipeline.orchestrator.modules.automation.runners.model.RunnerEngineInstanceAggregate;
import eu.venthe.pipeline.orchestrator.modules.automation.runners.model.RunnerEngineInstanceId;
import eu.venthe.pipeline.orchestrator.shared_kernel.DomainRepository;

public interface RunnerEngineInstanceRepository extends DomainRepository<RunnerEngineInstanceAggregate, RunnerEngineInstanceId> {
}
