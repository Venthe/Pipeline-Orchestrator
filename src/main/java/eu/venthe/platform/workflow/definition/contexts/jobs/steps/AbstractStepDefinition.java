package eu.venthe.platform.workflow.definition.contexts.jobs.steps;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.platform.shared_kernel.system_events.contexts.utilities.ContextUtilities;
import jakarta.annotation.Nullable;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AbstractStepDefinition {
    @Nullable
    private final String id;
    @Nullable
    private final String _if;
    @Nullable
    private final String name;
    @Nullable
    private final StepEnvironmentVariablesContext environmentVariables;
    @Nullable
    private final Boolean continueOnError;
    @Nullable
    private final Integer timeoutMinutes;

    protected AbstractStepDefinition(JsonNode _root) {
        ObjectNode root = ContextUtilities.validateIsObjectNode(_root);

        id = StepIdContext.create(root.get("id")).orElse(null);
        _if = StepIfContext.create(root.get("if")).orElse(null);
        name = StepNameContext.create(root.get("name")).orElse(null);
        environmentVariables = StepEnvironmentVariablesContext.create(root.get("env")).orElse(null);
        continueOnError = ContextUtilities.create(root.get("continueOnError"), ContextUtilities.toBoolean()).orElse(false);
        timeoutMinutes = ContextUtilities.create(root.get("timeoutMinutes"), ContextUtilities.toInteger()).orElse(360);
    }

    private static class StepIdContext {
        private static Optional<String> create(final JsonNode id) {
            throw new UnsupportedOperationException();
        }
    }

    private static class StepIfContext {
        private static Optional<String> create(final JsonNode id) {
            throw new UnsupportedOperationException();
        }
    }

    private static class StepNameContext {
        private static Optional<String> create(final JsonNode id) {
            throw new UnsupportedOperationException();
        }
    }

    public static class StepEnvironmentVariablesContext {
        private static Optional<StepEnvironmentVariablesContext> create(final JsonNode root) {
            throw new UnsupportedOperationException();
        }
    }
}
