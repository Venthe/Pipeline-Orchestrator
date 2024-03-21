package eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts.common;

import com.fasterxml.jackson.databind.JsonNode;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.model.git.Reference;

import java.util.Optional;

public class GitReferenceNameContext {

    public static Optional<Reference.Name> create(JsonNode before) {
        throw new UnsupportedOperationException();
    }

    public static Reference.Name ensure(JsonNode ref) {
        throw new UnsupportedOperationException();
    }
}
