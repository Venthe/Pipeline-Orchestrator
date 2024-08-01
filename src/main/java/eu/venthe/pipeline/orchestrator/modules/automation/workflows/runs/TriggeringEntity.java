package eu.venthe.pipeline.orchestrator.modules.automation.workflows.runs;

import eu.venthe.pipeline.orchestrator.modules.automation.workflows.runs._archive.Actor;

public interface TriggeringEntity {
    Actor getActor();

    WorkflowCorrelationId getCorrelationId();

}
