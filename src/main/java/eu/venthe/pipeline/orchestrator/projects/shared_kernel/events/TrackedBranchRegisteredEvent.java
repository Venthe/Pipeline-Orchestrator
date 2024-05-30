package eu.venthe.pipeline.orchestrator.projects.shared_kernel.events;

import eu.venthe.pipeline.orchestrator.shared_kernel.events.DomainTrigger;
import lombok.Value;

@Value
public class TrackedBranchRegisteredEvent implements DomainTrigger {
    String type = "tracked_branch_registered";
}
