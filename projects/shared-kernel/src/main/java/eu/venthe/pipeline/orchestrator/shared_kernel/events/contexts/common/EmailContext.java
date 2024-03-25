package eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts.common;

import com.fasterxml.jackson.databind.JsonNode;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts.utilities.ContextUtilities;

import java.util.Optional;

public class EmailContext {
    public static Optional<String> create(JsonNode email) {
        return ContextUtilities.Text.create(email);
    }
}
