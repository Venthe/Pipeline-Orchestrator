package eu.venthe.pipeline.workflows.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.google.common.collect.MoreCollectors;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static java.util.Arrays.stream;

@Getter
@RequiredArgsConstructor
public enum JobExecutionStatus {
    QUEUED("queued"),
    IN_PROGRESS("in_progress"),
    WAITING("waiting"),
    COMPLETED("completed"),
    NEUTRAL("neutral"),
    SUCCESS("success"),
    FAILURE("failure"),
    CANCELLED("cancelled"),
    ACTION_REQUIRED("action_required"),
    OUT("timed_out"),
    SKIPPED("skipped"),
    STALE("stale");

    private final String value;

    @Override
    public String toString() {
        return value;
    }

    @JsonCreator
    public static JobExecutionStatus of(String type) {
        if (type == null) {
            throw new IllegalArgumentException();
        }

        return stream(values())
                .filter(eventType -> eventType.getValue().equalsIgnoreCase(type.trim()))
                .collect(MoreCollectors.toOptional()).orElseThrow();
    }
}
