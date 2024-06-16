package eu.venthe.pipeline.orchestrator.workflow_executions.domain.infrastructure;

import eu.venthe.pipeline.orchestrator.shared_kernel.DomainRepository;
import eu.venthe.pipeline.orchestrator.workflow_executions.domain.WorkflowExecution;
import eu.venthe.pipeline.orchestrator.workflow_executions.domain.WorkflowExecutionId;

public interface WorkflowExecutionRepository extends DomainRepository<WorkflowExecution, WorkflowExecutionId> {
}

/*
package eu.venthe.pipeline.orchestrator.workflow_executions.domain;

import eu.venthe.pipeline.orchestrator.projects.domain.workflow_executions.WorkflowExecution;

import java.util.Optional;

@Deprecated
public interface WorkflowExecutionRepository {
    void save(WorkflowExecution workflowExecution);

    Optional<WorkflowExecution> get(String workflowExecutionId);
}
*/
