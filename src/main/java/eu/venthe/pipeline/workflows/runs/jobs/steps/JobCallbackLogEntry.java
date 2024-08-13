package eu.venthe.pipeline.workflows.runs.jobs.steps;

import java.time.OffsetDateTime;
import java.util.Map;

public record JobCallbackLogEntry(LogSeverity severity,
                                  OffsetDateTime timestamp,
                                  String message,
                                  Map.Entry<String, String>... structuredData) {
}
