package eu.venthe.pipeline.orchestrator.job_executor;

public interface JobExecutorCallbackService {
    void jobExecutionProgressed();
    void jobExecutionStarted();
    void jobExecutionCompleted();
}
