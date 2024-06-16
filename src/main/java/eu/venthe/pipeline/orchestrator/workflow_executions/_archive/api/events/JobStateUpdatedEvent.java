package eu.venthe.pipeline.orchestrator.workflow_executions._archive.api.events;

import eu.venthe.pipeline.orchestrator.shared_kernel.events.DomainTrigger;
import lombok.Value;

@Value
public class JobStateUpdatedEvent implements DomainTrigger {
    String type = "workflow_cancelled_event";
}
