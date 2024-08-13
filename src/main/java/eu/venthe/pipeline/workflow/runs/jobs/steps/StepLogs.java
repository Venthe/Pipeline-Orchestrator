package eu.venthe.pipeline.workflow.runs.jobs.steps;

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

    // TODO: Expand the logs
    void append(final JobCallbackLogEntry log) {
        logs.add(new Log(log.timestamp(), log.message()));
    }

    @Value
    @EqualsAndHashCode(onlyExplicitlyIncluded = true)
    private static class Log {
        @EqualsAndHashCode.Include
        OffsetDateTime timestamp;
        String value;
    }
}
