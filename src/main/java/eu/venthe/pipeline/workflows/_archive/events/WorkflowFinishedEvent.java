package eu.venthe.pipeline.workflows._archive.events;

import eu.venthe.pipeline.shared_kernel.events.DomainTrigger;
import lombok.Value;

@Value
public class WorkflowFinishedEvent implements DomainTrigger {
    String type = "workflow_finished_event";
}
