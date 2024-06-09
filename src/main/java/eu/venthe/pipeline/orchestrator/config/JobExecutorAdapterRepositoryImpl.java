package eu.venthe.pipeline.orchestrator.config;

import eu.venthe.pipeline.orchestrator.config.infrastructure.in_memory_repositroy.InMemoryDomainRepository;
import eu.venthe.pipeline.orchestrator.workflow_executions.domain.job_executions.adapters.template.model.AdapterId;
import eu.venthe.pipeline.orchestrator.workflow_executions.domain.job_executions.domain.infrastructure.JobExecutorAdapterRepository;
import eu.venthe.pipeline.orchestrator.workflow_executions.domain.job_executions.domain.model.AdapterInstanceAggregate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JobExecutorAdapterRepositoryImpl extends InMemoryDomainRepository<AdapterInstanceAggregate, AdapterId> implements JobExecutorAdapterRepository {
}
