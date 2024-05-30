package eu.venthe.pipeline.orchestrator.projects.shared_kernel.events;

import eu.venthe.pipeline.orchestrator.shared_kernel.events.DomainTrigger;
import lombok.Value;

@Value
public class ManualWorkflowRegisteredEvent implements DomainTrigger {
    String type = "manual_workflow_registered";
}
