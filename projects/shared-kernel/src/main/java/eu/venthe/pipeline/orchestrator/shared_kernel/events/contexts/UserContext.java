package eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.Optional;

// TODO: Implement context
// components/github-user.yaml#
public class UserContext {
    public static UserContext ensure(final JsonNode root) {

        throw new UnsupportedOperationException();
    }

    public static Optional<UserContext> create(final JsonNode approver) {
        throw new UnsupportedOperationException();
    }
}
