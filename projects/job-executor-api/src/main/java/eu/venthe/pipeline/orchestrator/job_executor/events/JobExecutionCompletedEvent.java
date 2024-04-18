package eu.venthe.pipeline.orchestrator.job_executor.events;

import eu.venthe.pipeline.orchestrator.shared_kernel.events.DomainEvent;
import lombok.Value;

/**
 * Event emitted when job is completed, failed, etc.
 */
@Value
public class JobExecutionCompletedEvent implements DomainEvent {
    String type = "job_execution_completed";
}
