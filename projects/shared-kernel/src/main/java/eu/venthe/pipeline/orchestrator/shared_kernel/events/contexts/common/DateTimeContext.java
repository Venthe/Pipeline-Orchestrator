package eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts.common;

import com.fasterxml.jackson.databind.JsonNode;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts.utilities.ContextUtilities;
import lombok.experimental.UtilityClass;

import java.time.OffsetDateTime;
import java.util.Optional;

import static java.time.format.DateTimeFormatter.ISO_OFFSET_DATE_TIME;

@UtilityClass
public class DateTimeContext {
    public static Optional<OffsetDateTime> create(final JsonNode root) {
        return ContextUtilities.Text.create(root)
                .map(dateTime -> OffsetDateTime.parse(dateTime, ISO_OFFSET_DATE_TIME));
    }

    public static OffsetDateTime ensure(final JsonNode root) {
        return create(root).orElseThrow(() -> new IllegalArgumentException("DateTime must be present"));
    }
}
