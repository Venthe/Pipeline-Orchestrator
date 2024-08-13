package eu.venthe.pipeline.application.config;

import eu.venthe.pipeline.application.infrastructure.in_memory_repository.InMemoryDomainRepository;
import eu.venthe.pipeline.workflows.runs.infrastructure.WorkflowRunRepository;
import org.springframework.stereotype.Component;

@Component
public class WorkflowRunRepositoryImpl extends InMemoryDomainRepository<WorkflowRunRepository.Aggregate, WorkflowRunRepository.Id> implements WorkflowRunRepository {
}
