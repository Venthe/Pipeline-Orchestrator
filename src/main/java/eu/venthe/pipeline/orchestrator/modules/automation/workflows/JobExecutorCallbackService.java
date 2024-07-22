package eu.venthe.pipeline.orchestrator.modules.automation.workflows;

import eu.venthe.pipeline.orchestrator.modules.automation.runners.adapters.template.JobExecutorAdapter;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.model.JobExecutionId;
import eu.venthe.pipeline.orchestrator.projects.domain.ProjectId;
import lombok.RequiredArgsConstructor;

import java.io.File;
import java.time.OffsetDateTime;
import java.util.Map;

public interface JobExecutorCallbackService {
    default InitialExecutionContext requestContext(CallMetadata callMetadata) {
        throw new UnsupportedOperationException();
    }

    default void jobExecutionProgressed(CallMetadata callMetadata) {
        throw new UnsupportedOperationException();
    }

    default void jobExecutionStarted(CallMetadata callMetadata) {
        throw new UnsupportedOperationException();
    }

    default void jobExecutionCompleted(CallMetadata callMetadata) {
        throw new UnsupportedOperationException();
    }

    default void uploadLog(Log log, CallMetadata callMetadata) {
        throw new UnsupportedOperationException();
    }

    default File downloadArtifact(CallMetadata callMetadata) {
        throw new UnsupportedOperationException();
    }

    default void uploadArtifact(File file, CallMetadata callMetadata) {
        throw new UnsupportedOperationException();
    }

    default void uploadCache(File file, CallMetadata callMetadata) {
        throw new UnsupportedOperationException();
    }

    default File downloadCache(CallMetadata callMetadata) {
        throw new UnsupportedOperationException();
    }

    record InitialExecutionContext() {
    }

    record CallMetadata(ProjectId projectId,
                        JobExecutionId executionId,
                        JobExecutorAdapter.CallbackToken callbackToken) {
    }

    record Log(Severity severity, OffsetDateTime timestamp, String message,
               Map.Entry<String, String>... structuredData) {
    }

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
