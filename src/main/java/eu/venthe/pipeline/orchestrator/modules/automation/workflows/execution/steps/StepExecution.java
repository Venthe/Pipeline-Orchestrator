package eu.venthe.pipeline.orchestrator.modules.automation.workflows.execution.steps;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

@Slf4j
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Getter
public class StepExecution {
    private final Supplier<OffsetDateTime> date;
    private final int order;
    @EqualsAndHashCode.Include
    private final Id id;
    private String name;
    private OffsetDateTime startedAt;
    private OffsetDateTime stoppedAt;
    /**
     * The result of a completed step after continue-on-error is applied.
     */
    private Status conclusion;
    /**
     * The result of a completed step before continue-on-error is applied.
     */
    private Status outcome;
    private StepLogs logs = new StepLogs();

    public StepExecution(final Supplier<OffsetDateTime> getNow,
                         final int order,
                         final String id,
                         String initialName) {
        date = getNow;
        this.order = order;
        this.id = new Id(id);
        updateName(initialName);
        conclusion = Status.PENDING;
        outcome = Status.PENDING;
    }

    public void updateName(String name) {
        this.name = name;
    }

    public void markStart(OffsetDateTime startedAt) {
        if (this.startedAt != null) {
            log.warn("Trying to change start time when it is already set. From={}, To={}", this.startedAt, startedAt);
            return;
        }

        if (startedAt.isAfter(stoppedAt)) {
            throw new IllegalArgumentException("Started at cannot be after stopped at. StartedAt=" + startedAt + ", StoppedAt=" + stoppedAt);
        }

        this.startedAt = startedAt;
    }

    public void markEnd(OffsetDateTime stoppedAt, Status outcome, Status conclusion) {
        if (isDone()) {
            log.warn("Trying to change values when step is already done. From={}, To={}",
                    Map.of("stoppedAt", this.stoppedAt, "outcome", this.outcome, "conclusion", this.conclusion),
                    Map.of("stoppedAt", stoppedAt, "outcome", outcome, "conclusion", conclusion)
            );
            return;
        }

        if (stoppedAt.isBefore(startedAt)) {
            throw new IllegalArgumentException("Stopped at cannot be before started at. StartedAt=" + startedAt + ", StoppedAt=" + stoppedAt);
        }

        this.stoppedAt = stoppedAt;
        this.conclusion = conclusion;
        this.outcome = outcome;
    }

    public void appendLog(OffsetDateTime timestamp, String _log) {
        log.debug("Appending log. Time={}, Value={}", timestamp, _log);
        logs.append(timestamp, _log);
    }

    public boolean isDone() {
        return ObjectUtils.anyNotNull(this.outcome, this.conclusion, this.stoppedAt);
    }

    public Optional<Duration> getTotalDuration() {
        if ((this.startedAt == null && this.stoppedAt == null)) {
            return Optional.empty();
        }
        if (startedAt == null) {
            log.warn("Started at should not be null when stopped at is defined. StartedAt={}, StoppedAt={}", startedAt, stoppedAt);
            return Optional.empty();
        }

        return Optional.of(Duration.between(startedAt, Optional.ofNullable(stoppedAt).orElseGet(date)));
    }

    public enum Status {
        SUCCESS,
        FAILURE,
        CANCELLED,
        SKIPPED,
        PENDING
    }

    public record Id(String value) {
    }
}
