package eu.venthe.pipeline.orchestrator.modules.automation.workflows._archive.events;

import eu.venthe.pipeline.orchestrator.shared_kernel.events.DomainTrigger;
import lombok.Value;

@Value
public class WorkflowStateUpdatedEvent implements DomainTrigger {
    String type = "workflow_finished_event";
}
