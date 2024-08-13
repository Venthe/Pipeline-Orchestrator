package eu.venthe.pipeline.workflow._archive.events;

import eu.venthe.pipeline.shared_kernel.events.DomainTrigger;
import lombok.Value;

@Value
public class WorkflowExecutionCancelledEvent implements DomainTrigger {
    String type = "workflow_execution_cancelled_event";
}
