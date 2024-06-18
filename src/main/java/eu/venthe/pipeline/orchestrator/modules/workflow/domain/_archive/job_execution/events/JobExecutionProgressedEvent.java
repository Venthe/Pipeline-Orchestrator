package eu.venthe.pipeline.orchestrator.modules.workflow.domain._archive.job_execution.events;

import eu.venthe.pipeline.orchestrator.shared_kernel.events.DomainTrigger;
import lombok.Value;

@Value
public class JobExecutionProgressedEvent implements DomainTrigger {
    String type = "job_execution_progressed";
}

