package eu.venthe.pipeline.orchestrator.config;

import eu.venthe.pipeline.orchestrator.infrastructure.in_memory_repository.InMemoryDomainRepository;
import eu.venthe.pipeline.orchestrator.modules.automation.runners.runner_engine.template.model.RunnerImplementationId;
import eu.venthe.pipeline.orchestrator.modules.automation.runners.infrastructure.JobExecutorAdapterRepository;
import eu.venthe.pipeline.orchestrator.modules.automation.runners.impl.model.AdapterInstanceAggregate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JobExecutorAdapterRepositoryImpl extends InMemoryDomainRepository<AdapterInstanceAggregate, RunnerImplementationId> implements JobExecutorAdapterRepository {
}
