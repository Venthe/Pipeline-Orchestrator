package eu.venthe.pipeline.orchestrator.modules.automation.workflows.execution._archive.events;

import eu.venthe.pipeline.orchestrator.shared_kernel.events.DomainTrigger;
import lombok.Value;

@Value
public class JobExecutionStartedEvent implements DomainTrigger {
    String type = "job_execution_started";
}
