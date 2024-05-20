package eu.venthe.pipeline.orchestrator.job_executor;

public interface JobExecutorCommandService {
    void triggerJobExecution(Object command);
}
