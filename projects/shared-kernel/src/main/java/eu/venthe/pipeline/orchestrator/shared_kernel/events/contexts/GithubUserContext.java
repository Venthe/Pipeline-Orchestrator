package eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.Optional;

// components/github-user.yaml#
public class GithubUserContext {
    public static GithubUserContext ensure(JsonNode root) {

        throw new UnsupportedOperationException();
    }

    public static Optional<GithubUserContext> create(JsonNode approver) {
        throw new UnsupportedOperationException();
    }
}
