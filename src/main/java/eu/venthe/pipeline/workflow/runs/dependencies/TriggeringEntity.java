package eu.venthe.pipeline.workflow.runs.dependencies;

import eu.venthe.pipeline.workflow.runs.WorkflowCorrelationId;

public interface TriggeringEntity {
    Actor getActor();

    WorkflowCorrelationId getCorrelationId();

}
