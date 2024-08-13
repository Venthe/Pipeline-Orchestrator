package eu.venthe.platform.workflow.definition.contexts.jobs.steps;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.platform.shared_kernel.system_events.contexts.utilities.ContextUtilities;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.util.Optional;

public class UsesStepDefinition extends AbstractStepDefinition {
    @Nonnull
    private final String uses;
    @Nullable
    private final WithContext with;

    public UsesStepDefinition(@Nullable final String id,
                              @Nullable final String _if,
                              @Nullable final String name,
                              @Nullable final StepEnvironmentVariablesContext environmentVariables,
                              @Nullable final Boolean continueOnError,
                              @Nullable final Integer timeoutMinutes,
                              @Nonnull final String uses,
                              @Nullable final WithContext with) {
        super(id, _if, name, environmentVariables, continueOnError, timeoutMinutes);
        this.uses = uses;
        this.with = with;
    }

    public UsesStepDefinition(JsonNode _root) {
        super(_root);
        ObjectNode root = ContextUtilities.validateIsObjectNode(_root);

        uses = UsesContext.ensure(root.get("uses"));
        with = WithContext.create(root.get("with")).orElse(null);
    }

    private static class UsesContext {
        private static String ensure(final JsonNode id) {
            throw new UnsupportedOperationException();
        }
    }

    private static class WithContext {
        private static Optional<WithContext> create(final JsonNode root) {
            throw new UnsupportedOperationException();
        }
    }
}
