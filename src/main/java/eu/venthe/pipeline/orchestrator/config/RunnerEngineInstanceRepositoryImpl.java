package eu.venthe.pipeline.orchestrator.config;

import eu.venthe.pipeline.orchestrator.infrastructure.in_memory_repository.InMemoryDomainRepository;
import eu.venthe.pipeline.runners.model.RunnerEngineInstanceId;
import eu.venthe.pipeline.runners.infrastructure.RunnerEngineInstanceRepository;
import eu.venthe.pipeline.runners.model.RunnerEngineInstanceAggregate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RunnerEngineInstanceRepositoryImpl extends InMemoryDomainRepository<RunnerEngineInstanceAggregate, RunnerEngineInstanceId> implements RunnerEngineInstanceRepository {
}
