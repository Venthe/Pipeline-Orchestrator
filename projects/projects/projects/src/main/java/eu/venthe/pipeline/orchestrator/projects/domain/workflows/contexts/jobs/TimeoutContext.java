package eu.venthe.pipeline.orchestrator.projects.domain.workflows.contexts.jobs;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.Optional;

// TODO: Implement context
public class TimeoutContext {
    public static Optional<Integer> create(JsonNode timeoutMinutes) {
        throw new UnsupportedOperationException();
    }
}
