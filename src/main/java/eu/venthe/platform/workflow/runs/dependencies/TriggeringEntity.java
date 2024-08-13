package eu.venthe.platform.workflow.runs.dependencies;

import eu.venthe.platform.workflow.runs.WorkflowCorrelationId;

public interface TriggeringEntity {
    Actor getActor();

    WorkflowCorrelationId getCorrelationId();

}
