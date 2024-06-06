package eu.venthe.pipeline.orchestrator.config;

import eu.venthe.pipeline.orchestrator.infrastructure.in_memory_repositroy.InMemoryDomainRepository;
import eu.venthe.pipeline.orchestrator.job_executor.adapters.template.model.AdapterId;
import eu.venthe.pipeline.orchestrator.job_executor.domain.infrastructure.JobExecutorAdapterRepository;
import eu.venthe.pipeline.orchestrator.job_executor.domain.model.AdapterInstanceAggregate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JobExecutorAdapterRepositoryImpl extends InMemoryDomainRepository<AdapterInstanceAggregate, AdapterId> implements JobExecutorAdapterRepository {
}
