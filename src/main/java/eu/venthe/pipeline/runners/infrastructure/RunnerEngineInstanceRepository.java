package eu.venthe.pipeline.runners.infrastructure;

import eu.venthe.pipeline.runners.model.RunnerEngineInstanceAggregate;
import eu.venthe.pipeline.runners.model.RunnerEngineInstanceId;
import eu.venthe.pipeline.shared_kernel.DomainRepository;

public interface RunnerEngineInstanceRepository extends DomainRepository<RunnerEngineInstanceAggregate, RunnerEngineInstanceId> {
}
