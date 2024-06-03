package eu.venthe.pipeline.orchestrator.job_executor._archive._unsorted;

public interface ExecutorManager {

    void registerExecutor(String executorProviderId);

    enum OperatingSystem {
        WINDOWS,
        LINUX,
        MACOS
    }

    enum Architecture {
        X64,
        ARM64,
        ARM32
    }
}
