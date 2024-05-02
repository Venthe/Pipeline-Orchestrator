package eu.venthe.pipeline.orchestrator.job_executor.events;

import eu.venthe.pipeline.orchestrator.shared_kernel.events.DomainEvent;
import lombok.Value;

@Value
public class JobExecutionProgressedEvent implements DomainEvent {
    String type = "job_execution_progressed";
}

