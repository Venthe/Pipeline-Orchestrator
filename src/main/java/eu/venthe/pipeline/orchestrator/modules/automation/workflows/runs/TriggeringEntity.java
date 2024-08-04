package eu.venthe.pipeline.orchestrator.modules.automation.workflows.runs;

public interface TriggeringEntity {
    Actor getActor();

    WorkflowCorrelationId getCorrelationId();

}
