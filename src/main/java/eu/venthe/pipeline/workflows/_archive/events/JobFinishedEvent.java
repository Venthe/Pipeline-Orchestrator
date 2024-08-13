package eu.venthe.pipeline.workflows._archive.events;

import eu.venthe.pipeline.shared_kernel.events.DomainTrigger;
import lombok.Value;

@Value
public class JobFinishedEvent implements DomainTrigger {
    String type = "workflow_cancelled_event";
}
