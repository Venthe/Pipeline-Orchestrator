package eu.venthe.pipeline.orchestrator.config;

import eu.venthe.pipeline.orchestrator.infrastructure.in_memory_repository.InMemoryDomainRepository;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.runs.infrastructure.WorkflowRunRepository;
import org.springframework.stereotype.Component;

@Component
public class WorkflowRunRepositoryImpl extends InMemoryDomainRepository<WorkflowRunRepository.Aggregate, WorkflowRunRepository.Id> implements WorkflowRunRepository {
}
