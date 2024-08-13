package eu.venthe.pipeline.workflows.runs._archive.events;

import eu.venthe.pipeline.shared_kernel.events.DomainTrigger;
import lombok.Value;

/**
 * Event emitted when job is completed, failed, etc.
 */
@Value
public class JobExecutionCompletedEvent implements DomainTrigger {
    String type = "job_execution_completed";
}
