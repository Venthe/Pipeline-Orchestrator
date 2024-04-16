package eu.venthe.pipeline.orchestrator.projects.api.events;

import eu.venthe.pipeline.orchestrator.shared_kernel.events.DomainEvent;
import lombok.Value;

@Value
public class TrackedBranchRegisteredEvent implements DomainEvent {
    String type = "tracked_branch_registered";
}
