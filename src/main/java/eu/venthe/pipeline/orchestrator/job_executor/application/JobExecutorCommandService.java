package eu.venthe.pipeline.orchestrator.job_executor.application;

public interface JobExecutorCommandService {
    void triggerJobExecution(Object command);
}
