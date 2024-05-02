package eu.venthe.pipeline.orchestrator.task_scheduler.api.events;

import eu.venthe.pipeline.orchestrator.shared_kernel.events.DomainEvent;
import lombok.Value;

@Value
public class ScheduledTaskUnregistered implements DomainEvent {
    String type = "scheduled_task_unregistered";
}
