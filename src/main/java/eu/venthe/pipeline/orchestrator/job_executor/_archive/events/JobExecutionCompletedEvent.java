package eu.venthe.pipeline.orchestrator.job_executor._archive.events;

import eu.venthe.pipeline.orchestrator.shared_kernel.events.DomainTrigger;
import lombok.Value;

/**
 * Event emitted when job is completed, failed, etc.
 */
@Value
public class JobExecutionCompletedEvent implements DomainTrigger {
    String type = "job_execution_completed";
}
