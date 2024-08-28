package eu.venthe.platform.workflow.definition.contexts.jobs.steps;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.platform.shared_kernel.system_events.contexts.utilities.ContextUtilities;
import eu.venthe.platform.workflow.data_interpolation.Expression;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import lombok.Builder;
import lombok.ToString;

@ToString
public class RunStepDefinition extends AbstractStepDefinition {
    @Nonnull
    private final String run;
    @Nullable
    private final String workflowDirectory;
    @Nullable
    private final String shell;

    @Builder
    public RunStepDefinition(@Nullable final String id,
                             @Nullable final Expression<String> _if,
                             @Nullable final Expression<String> name,
                             @Nullable final StepEnvironmentVariablesContext environmentVariables,
                             @Nullable final Boolean continueOnError,
                             @Nullable final Integer timeoutMinutes,
                             @Nonnull final String run,
                             @Nullable final String workflowDirectory,
                             @Nullable final String shell) {
        super(id, _if, name, environmentVariables, continueOnError, timeoutMinutes);
        this.run = run;
        this.workflowDirectory = workflowDirectory;
        this.shell = shell;
    }

    public RunStepDefinition(JsonNode _root) {
        super(_root);
        ObjectNode root = ContextUtilities.validateIsObjectNode(_root);

        run = ContextUtilities.ensure(root.get("run"), ContextUtilities.toText());
        workflowDirectory = ContextUtilities.create(root.get("workflowDirectory"), ContextUtilities.toText()).orElse(null);
        shell = ContextUtilities.create(root.get("shell"), ContextUtilities.toText()).orElse(null);
    }
}
