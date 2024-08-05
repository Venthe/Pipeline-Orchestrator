package eu.venthe.pipeline.orchestrator.modules.automation.workflows.runs.dependencies;

import eu.venthe.pipeline.orchestrator.modules.automation.workflows.runs.WorkflowCorrelationId;

public interface TriggeringEntity {
    Actor getActor();

    WorkflowCorrelationId getCorrelationId();

}
