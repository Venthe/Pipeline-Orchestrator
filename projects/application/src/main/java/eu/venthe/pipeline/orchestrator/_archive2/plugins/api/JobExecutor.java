package eu.venthe.pipeline.orchestrator._archive2.plugins.api;

public interface JobExecutor {
    void queueStepped(String id, String jobId);
}
