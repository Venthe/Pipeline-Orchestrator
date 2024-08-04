package eu.venthe.pipeline.orchestrator.modules.automation.workflows.runs.jobs.steps;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.Value;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
public class StepLogs {
    private final List<Log> logs = new ArrayList<>();

    void append(final OffsetDateTime timestamp, final String log) {
        logs.add(new Log(timestamp, log));
    }

    @Value
    @EqualsAndHashCode(onlyExplicitlyIncluded = true)
    private static class Log {
        @EqualsAndHashCode.Include
        OffsetDateTime timestamp;
        String value;
    }
}
