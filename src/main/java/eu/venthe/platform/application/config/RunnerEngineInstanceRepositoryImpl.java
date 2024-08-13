package eu.venthe.platform.application.config;

import eu.venthe.platform.application.infrastructure.in_memory_repository.InMemoryDomainRepository;
import eu.venthe.platform.runner.model.RunnerEngineInstanceId;
import eu.venthe.platform.runner.infrastructure.RunnerEngineInstanceRepository;
import eu.venthe.platform.runner.model.RunnerEngineInstanceAggregate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RunnerEngineInstanceRepositoryImpl extends InMemoryDomainRepository<RunnerEngineInstanceAggregate, RunnerEngineInstanceId> implements RunnerEngineInstanceRepository {
}
