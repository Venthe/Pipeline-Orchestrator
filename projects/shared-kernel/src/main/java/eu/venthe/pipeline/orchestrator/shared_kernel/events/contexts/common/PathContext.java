package eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts.common;

import com.fasterxml.jackson.databind.JsonNode;

import java.nio.file.Path;
import java.util.List;

public class PathContext {
    public static Path ensure(final JsonNode workflow) {
        throw new UnsupportedOperationException();
    }

    public static List<String> list(JsonNode added) {
        throw new UnsupportedOperationException();
    }
}
