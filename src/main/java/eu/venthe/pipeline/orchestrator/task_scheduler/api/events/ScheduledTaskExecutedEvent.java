package eu.venthe.pipeline.orchestrator.task_scheduler.api.events;

import eu.venthe.pipeline.orchestrator.shared_kernel.events.DomainTrigger;
import lombok.Value;

@Value
public class ScheduledTaskExecutedEvent implements DomainTrigger {
    String type = "scheduled_task_executed";
}
