package eu.venthe.pipeline.orchestrator.plugins.job_executors;

public interface JobExecutor {
    void queueStepped(String id, String jobId);
}
