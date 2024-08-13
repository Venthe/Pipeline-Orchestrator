package eu.venthe.pipeline.runner.infrastructure;

import eu.venthe.pipeline.runner.model.RunnerEngineInstanceAggregate;
import eu.venthe.pipeline.runner.model.RunnerEngineInstanceId;
import eu.venthe.pipeline.shared_kernel.DomainRepository;

public interface RunnerEngineInstanceRepository extends DomainRepository<RunnerEngineInstanceAggregate, RunnerEngineInstanceId> {
}
