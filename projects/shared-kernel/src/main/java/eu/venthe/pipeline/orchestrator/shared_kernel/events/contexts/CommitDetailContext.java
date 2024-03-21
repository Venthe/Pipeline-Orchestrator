package eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;
import java.util.Optional;

// components/commit-details.yaml#
public class CommitDetailContext {
    public static List<CommitDetailContext> list(JsonNode commits) {
        throw new UnsupportedOperationException();
    }

    public static Optional<CommitDetailContext> create(JsonNode headCommit) {
        throw new UnsupportedOperationException();
    }
}
