package eu.venthe.pipeline.orchestrator.workflow_executions.domain.job_executions.application;

import eu.venthe.pipeline.orchestrator.workflow_executions.domain.job_executions.adapters.template.JobExecutorAdapter;
import eu.venthe.pipeline.orchestrator.workflow_executions.domain.job_executions.domain.model.ExecutionId;
import eu.venthe.pipeline.orchestrator.projects.domain.model.ProjectId;
import lombok.RequiredArgsConstructor;

import java.io.File;
import java.time.OffsetDateTime;
import java.util.Map;

public interface JobExecutorCallbackService {
    InitialExecutionContext requestContext(CallMetadata callMetadata);

    void jobExecutionProgressed(CallMetadata callMetadata);

    void jobExecutionStarted(CallMetadata callMetadata);

    void jobExecutionCompleted(CallMetadata callMetadata);

    void uploadLog(Log log, CallMetadata callMetadata);

    File downloadArtifact(CallMetadata callMetadata);

    void uploadArtifact(File file, CallMetadata callMetadata);

    void uploadCache(File file, CallMetadata callMetadata);

    File downloadCache(CallMetadata callMetadata);

    void downloadAction(CallMetadata callMetadata);
    // GIT LFS

    record InitialExecutionContext() {
    }

    record CallMetadata(ProjectId projectId,
                        ExecutionId executionId,
                        JobExecutorAdapter.CallbackToken callbackToken) {
    }

    record Log(Severity severity, OffsetDateTime timestamp, String message, Map.Entry<String, String>... structuredData) {}

    /**
     * RFC-5424
     */
    @RequiredArgsConstructor
    enum Severity {
        /**
         * System is unusable
         * <p>
         * A panic condition.
         */
        EMERGENCY(0, "emerg"),
        /**
         * Action must be taken immediately
         * <p>
         * A condition that should be corrected immediately, such as a corrupted system database.
         */
        ALERT(1, "alert"),
        /**
         * Critical conditions
         * <p>
         * I.e. Hard device errors.
         */
        CRITICAL(2, "crit"),
        /**
         * Error conditions
         */
        ERROR(3, "err"),
        /**
         * Error conditions
         */
        WARNING(4, "warning"),
        /**
         * Normal but significant conditions
         * <p>
         * Conditions that are not error conditions, but that may require special handling.
         */
        NOTICE(5, "notice"),
        /**
         * Informational messages
         * <p>
         * Confirmation that the program is working as expected.
         */
        INFORMATIONAL(6, "info"),
        /**
         * Debug-level messages
         * <p>
         * Messages that contain information normally of use only when debugging a program.
         */
        DEBUG(7, "debug");

        final int level;
        final String keyword;
    }
}
