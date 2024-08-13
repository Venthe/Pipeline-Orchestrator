package eu.venthe.platform.task_scheduler.api.events;

import eu.venthe.platform.shared_kernel.events.DomainTrigger;
import lombok.Value;

@Value
public class ScheduledTaskRegisteredEvent implements DomainTrigger {
    String type = "scheduled_task_registered";
}
