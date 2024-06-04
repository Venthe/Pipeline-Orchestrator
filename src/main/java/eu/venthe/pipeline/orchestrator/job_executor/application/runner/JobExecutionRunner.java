package eu.venthe.pipeline.orchestrator.job_executor.application.runner;

public interface JobExecutionRunner {

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
