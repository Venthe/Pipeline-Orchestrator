package eu.venthe.pipeline.workflows.runs._archive.events;

import eu.venthe.pipeline.shared_kernel.events.DomainTrigger;
import lombok.Value;

@Value
public class JobExecutionQueued implements DomainTrigger {
    String type = "job_execution_queued";
}
