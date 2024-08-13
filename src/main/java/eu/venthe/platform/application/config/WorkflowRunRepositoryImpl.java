package eu.venthe.platform.application.config;

import eu.venthe.platform.application.infrastructure.in_memory_repository.InMemoryDomainRepository;
import eu.venthe.platform.workflow.runs.infrastructure.WorkflowRunRepository;
import org.springframework.stereotype.Component;

@Component
public class WorkflowRunRepositoryImpl extends InMemoryDomainRepository<WorkflowRunRepository.Aggregate, WorkflowRunRepository.Id> implements WorkflowRunRepository {
}
