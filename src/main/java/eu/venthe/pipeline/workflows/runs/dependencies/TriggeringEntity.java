package eu.venthe.pipeline.workflows.runs.dependencies;

import eu.venthe.pipeline.workflows.runs.WorkflowCorrelationId;

public interface TriggeringEntity {
    Actor getActor();

    WorkflowCorrelationId getCorrelationId();

}
