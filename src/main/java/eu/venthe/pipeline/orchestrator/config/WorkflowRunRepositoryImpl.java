package eu.venthe.pipeline.orchestrator.config;

import eu.venthe.pipeline.orchestrator.infrastructure.in_memory_repository.InMemoryDomainRepository;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.runs.WorkflowRun;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.runs._archive._1.infrastructure.WorkflowRunRepository;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.runs.WorkflowRunId;
import org.springframework.stereotype.Component;

@Component
public class WorkflowRunRepositoryImpl extends InMemoryDomainRepository<WorkflowRun, WorkflowRunId> implements WorkflowRunRepository {
}
