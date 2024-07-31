package eu.venthe.pipeline.orchestrator.modules.automation.workflows.execution;

import lombok.RequiredArgsConstructor;

/**
 * RFC-5424
 */
@RequiredArgsConstructor
public
enum LogSeverity {
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
