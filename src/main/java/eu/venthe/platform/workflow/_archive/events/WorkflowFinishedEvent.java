package eu.venthe.platform.workflow._archive.events;

import eu.venthe.platform.shared_kernel.events.DomainTrigger;
import lombok.Value;

@Value
public class WorkflowFinishedEvent implements DomainTrigger {
    String type = "workflow_finished_event";
}
