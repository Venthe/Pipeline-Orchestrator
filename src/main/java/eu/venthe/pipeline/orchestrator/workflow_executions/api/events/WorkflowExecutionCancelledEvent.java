package eu.venthe.pipeline.orchestrator.workflow_executions.api.events;

import eu.venthe.pipeline.orchestrator.shared_kernel.events.DomainTrigger;
import lombok.Value;

@Value
public class WorkflowExecutionCancelledEvent implements DomainTrigger {
    String type = "workflow_execution_cancelled_event";
}
