package eu.venthe.platform.workflow.runs._archive.events;

import eu.venthe.platform.shared_kernel.events.DomainTrigger;
import lombok.Value;

@Value
public class JobExecutionQueued implements DomainTrigger {
    String type = "job_execution_queued";
}