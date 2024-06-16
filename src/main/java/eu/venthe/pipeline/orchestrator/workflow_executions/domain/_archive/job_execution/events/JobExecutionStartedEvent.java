package eu.venthe.pipeline.orchestrator.workflow_executions.domain._archive.job_execution.events;

import eu.venthe.pipeline.orchestrator.shared_kernel.events.DomainTrigger;
import lombok.Value;

@Value
public class JobExecutionStartedEvent implements DomainTrigger {
    String type = "job_execution_started";
}
