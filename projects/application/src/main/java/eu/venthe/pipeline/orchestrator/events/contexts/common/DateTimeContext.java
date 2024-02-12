package eu.venthe.pipeline.orchestrator.events.contexts.common;

import com.fasterxml.jackson.databind.JsonNode;
import eu.venthe.pipeline.orchestrator.utilities.ContextUtilities;

import java.time.OffsetDateTime;
import java.util.Optional;

import static java.time.format.DateTimeFormatter.ISO_OFFSET_DATE_TIME;

public class DateTimeContext {
    public static Optional<OffsetDateTime> create(JsonNode root) {
        return ContextUtilities.toTextual(root)
                .map(dateTime -> OffsetDateTime.parse(dateTime, ISO_OFFSET_DATE_TIME));
    }

    public static OffsetDateTime ensure(JsonNode root) {
        return create(root).orElseThrow(() -> new IllegalArgumentException("DateTime must be present"));
    }
}
