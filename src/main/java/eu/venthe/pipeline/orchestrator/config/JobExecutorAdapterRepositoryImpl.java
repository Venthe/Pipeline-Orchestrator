package eu.venthe.pipeline.orchestrator.config;

import eu.venthe.pipeline.orchestrator.infrastructure.in_memory_repository.InMemoryDomainRepository;
import eu.venthe.pipeline.orchestrator.modules.automation.runners.runner_engine.template.model.RunnerEngineInstanceId;
import eu.venthe.pipeline.orchestrator.modules.automation.runners.infrastructure.JobExecutorAdapterRepository;
import eu.venthe.pipeline.orchestrator.modules.automation.runners.impl.model.RunnerEngineInstanceAggregate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JobExecutorAdapterRepositoryImpl extends InMemoryDomainRepository<RunnerEngineInstanceAggregate, RunnerEngineInstanceId> implements JobExecutorAdapterRepository {
}
