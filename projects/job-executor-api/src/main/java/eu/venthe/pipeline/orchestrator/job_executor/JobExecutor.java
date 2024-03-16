package eu.venthe.pipeline.orchestrator.job_executor;

public interface JobExecutor {
    void triggerJob(TriggerJobCommand command);
}
