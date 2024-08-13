package eu.venthe.platform.workflow._archive.events;

import eu.venthe.platform.shared_kernel.events.DomainTrigger;
import lombok.Value;

@Value
public class JobFinishedEvent implements DomainTrigger {
    String type = "workflow_cancelled_event";
}
