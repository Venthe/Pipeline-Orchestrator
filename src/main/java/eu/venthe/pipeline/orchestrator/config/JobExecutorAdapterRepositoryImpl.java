package eu.venthe.pipeline.orchestrator.config;

import eu.venthe.pipeline.orchestrator.infrastructure.in_memory_repository.InMemoryDomainRepository;
import eu.venthe.pipeline.orchestrator.modules.workflow.domain.job_executions.adapters.template.model.AdapterId;
import eu.venthe.pipeline.orchestrator.modules.workflow.domain.infrastructure.JobExecutorAdapterRepository;
import eu.venthe.pipeline.orchestrator.modules.workflow.domain.job_executions.AdapterInstanceAggregate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JobExecutorAdapterRepositoryImpl extends InMemoryDomainRepository<AdapterInstanceAggregate, AdapterId> implements JobExecutorAdapterRepository {
}
