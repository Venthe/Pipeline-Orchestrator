package eu.venthe.pipeline.orchestrator.config;

import eu.venthe.pipeline.orchestrator.infrastructure.in_memory_repository.InMemoryDomainRepository;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.execution.infrastructure.WorkflowExecutionRepository;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.execution.model.workflow_execution.WorkflowExecution;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.execution.model.workflow_execution.WorkflowExecutionId;
import org.springframework.stereotype.Component;

@Component
public class WorkflowExecutionRepositoryImpl extends InMemoryDomainRepository<WorkflowExecution, WorkflowExecutionId> implements WorkflowExecutionRepository {
}
