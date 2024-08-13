package eu.venthe.pipeline.workflow.runs.jobs.steps;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

@Slf4j
@ToString
@EqualsAndHashCode
public class StepsExecution {
    private final Map<StepExecution.Id, StepExecution> stepExecutions;

    public StepsExecution(final Supplier<OffsetDateTime> getNow, StepExecutionSpecification... steps) {
        var newSteps = Arrays.stream(steps).map(e -> new StepExecution(getNow, e.order(), e.id(), e.name())).toList();
        if (newSteps.stream().distinct().count() != steps.length) {
            throw new IllegalArgumentException("Step ID's are not unique. [" + newSteps.stream().map(StepExecution::getId).toList() + "]");
        }

        log.debug("Registering steps {}", newSteps);
        stepExecutions = newSteps.stream().distinct().collect(Collectors.toMap(StepExecution::getId, UnaryOperator.identity()));
    }

    public void updateName(StepExecution.Id stepId, String name) {
        log.debug("Updating name for step {} to {}", stepId, name);
        getStep(stepId).updateName(name);
    }

    public void markStart(StepExecution.Id stepId, OffsetDateTime startedAt) {
        log.debug("Marking start for step {} at {}", stepId, startedAt);
        getStep(stepId).markStart(startedAt);
    }

    public void markEnd(StepExecution.Id stepId, OffsetDateTime stoppedAt, StepExecution.Status outcome, StepExecution.Status conclusion) {
        log.debug("Ending step {} at {}. outcome={}, conclusion={}", stepId, stoppedAt, outcome, conclusion);
        getStep(stepId).markEnd(stoppedAt, outcome, conclusion);
    }

    private StepExecution getStep(StepExecution.Id stepId) {
        return Optional.ofNullable(stepExecutions.get(stepId)).orElseThrow();
    }

    public Status getStatus() {
        var conclusions = stepExecutions.values().stream()
                .map(StepExecution::getConclusion)
                .collect(Collectors.toSet());

        if (conclusions.contains(StepExecution.Status.PENDING)) {
            return Status.PENDING;
        }

        if (conclusions.contains(StepExecution.Status.FAILURE)) {
            return Status.FAILURE;
        }

        if (conclusions.contains(StepExecution.Status.CANCELLED)) {
            return Status.CANCELLED;
        }

        return Status.SUCCESS;
    }

    public boolean isDone() {
        return getStatus() == Status.PENDING;
    }

    public Duration getTotalDuration() {
        return stepExecutions.values().stream().map(StepExecution::getTotalDuration).flatMap(Optional::stream).reduce(Duration.ZERO, Duration::plus);
    }

    public void appendLog(final StepExecution.Id stepId, final JobCallbackLogEntry _log) {
        log.debug("Appending log for step {}", stepId);
        getStep(stepId).appendLog(_log);
    }

    public void cancel() {
        throw new UnsupportedOperationException();
    }

    public record StepExecutionSpecification(int order, String id, String name) {
    }

    public enum Status {
        SUCCESS,
        FAILURE,
        CANCELLED,
        SKIPPED,
        PENDING
    }
}
