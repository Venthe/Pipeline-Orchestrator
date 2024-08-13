package eu.venthe.platform.runner.infrastructure;

import eu.venthe.platform.runner.model.RunnerEngineInstanceAggregate;
import eu.venthe.platform.runner.model.RunnerEngineInstanceId;
import eu.venthe.platform.shared_kernel.DomainRepository;

public interface RunnerEngineInstanceRepository extends DomainRepository<RunnerEngineInstanceAggregate, RunnerEngineInstanceId> {
}
