package eu.venthe.pipeline.orchestrator.workflow_executions.domain;

import eu.venthe.pipeline.orchestrator.shared_kernel.Aggregate;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class WorkflowExecution implements Aggregate<WorkflowExecutionId> {
    @Getter
    @EqualsAndHashCode.Include
    WorkflowExecutionId id;
}
