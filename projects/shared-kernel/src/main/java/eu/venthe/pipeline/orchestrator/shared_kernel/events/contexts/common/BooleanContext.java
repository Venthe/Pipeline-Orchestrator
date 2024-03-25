package eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts.common;

import com.fasterxml.jackson.databind.JsonNode;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts.utilities.ContextUtilities;

import java.util.Optional;

public class BooleanContext {
    public static Optional<Boolean> create(final JsonNode bool) {
        return ContextUtilities.create(bool, BooleanContext::toBool);
    }

    public static Boolean ensure(JsonNode bool) {
        return ContextUtilities.ensure(bool, BooleanContext::toBool);
    }

    private static boolean toBool(JsonNode e) {
        if(e.isNull() || e.isMissingNode() || !e.isBoolean()) {
            throw new IllegalArgumentException();
        }

        return e.asBoolean();
    }
}
