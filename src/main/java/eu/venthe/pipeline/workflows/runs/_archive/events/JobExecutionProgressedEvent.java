package eu.venthe.pipeline.workflows.runs._archive.events;

import eu.venthe.pipeline.shared_kernel.events.DomainTrigger;
import lombok.Value;

@Value
public class JobExecutionProgressedEvent implements DomainTrigger {
    String type = "job_execution_progressed";
}

