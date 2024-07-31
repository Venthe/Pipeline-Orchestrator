package eu.venthe.pipeline.orchestrator.modules.automation.workflows.execution;

import java.time.OffsetDateTime;
import java.util.Map;

public record JobCallbackLogEntry(LogSeverity severity, OffsetDateTime timestamp,
                                  String message,
                                  Map.Entry<String, String>... structuredData) {
}