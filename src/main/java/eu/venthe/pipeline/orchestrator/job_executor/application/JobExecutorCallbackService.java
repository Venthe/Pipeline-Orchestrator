package eu.venthe.pipeline.orchestrator.job_executor.application;

public interface JobExecutorCallbackService {
    void jobExecutionProgressed();
    void jobExecutionStarted();
    void jobExecutionCompleted();
    void downloadJobSummary();
    void uploadLog();
    void downloadArtifact();
    void uploadArtifact();
    void uploadCache();
    void downloadCache();
    void downloadAction();
    void registerSession();
    // GIT LFS
}
