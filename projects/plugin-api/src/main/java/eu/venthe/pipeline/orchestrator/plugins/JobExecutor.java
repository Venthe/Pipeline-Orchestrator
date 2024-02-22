package eu.venthe.pipeline.orchestrator.plugins;

public interface JobExecutor {
    void queueStepped(String workflowExecutionId, String jobId);
}
