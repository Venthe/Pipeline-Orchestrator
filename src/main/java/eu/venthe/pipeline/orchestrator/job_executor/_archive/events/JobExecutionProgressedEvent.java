package eu.venthe.pipeline.orchestrator.job_executor._archive.events;

import eu.venthe.pipeline.orchestrator.shared_kernel.events.DomainTrigger;
import lombok.Value;

@Value
public class JobExecutionProgressedEvent implements DomainTrigger {
    String type = "job_execution_progressed";
}

