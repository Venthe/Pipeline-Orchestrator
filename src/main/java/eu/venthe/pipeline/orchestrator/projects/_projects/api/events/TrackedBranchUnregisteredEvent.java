package eu.venthe.pipeline.orchestrator.projects._projects.api.events;

import eu.venthe.pipeline.orchestrator.shared_kernel.events.DomainEvent;
import lombok.Value;

@Value
public class TrackedBranchUnregisteredEvent implements DomainEvent {
    String type = "tracked_branch_unregistered";
}
