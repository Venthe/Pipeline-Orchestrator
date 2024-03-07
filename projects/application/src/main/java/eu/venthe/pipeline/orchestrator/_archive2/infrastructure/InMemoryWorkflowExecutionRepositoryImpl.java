package eu.venthe.pipeline.orchestrator._archive2.infrastructure;

import eu.venthe.pipeline.orchestrator._archive2.workflow_executions.WorkflowExecution;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
public class InMemoryWorkflowExecutionRepositoryImpl implements WorkflowExecutionRepository {
    private final Set<WorkflowExecution> data = new HashSet<>();

    @Override
    public void save(WorkflowExecution workflowExecution) {
        data.add(workflowExecution);
    }

    @Override
    public Optional<WorkflowExecution> get(String workflowExecutionId) {
        return data.stream()
                .filter(e -> e.getId().equals(workflowExecutionId))
                .findFirst();
    }
}
