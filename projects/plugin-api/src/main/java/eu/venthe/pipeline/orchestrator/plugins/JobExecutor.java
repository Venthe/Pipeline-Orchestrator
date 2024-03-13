package eu.venthe.pipeline.orchestrator.plugins;

public interface JobExecutor {
    void queueStepped(String id, String jobId);
}
