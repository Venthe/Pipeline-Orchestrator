package eu.venthe.pipeline.workflows.runs._archive.run_context;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.pipeline.application.modules.automation.workflows.runs._archive.run_context.contexts.*;
import eu.venthe.pipeline.shared_kernel.system_events.contexts.utilities.ContextUtilities;
import eu.venthe.pipeline.workflows.runs._archive.run_context.contexts.*;
import jakarta.annotation.Nullable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@ToString
@EqualsAndHashCode
@SuperBuilder
abstract class AbstractJobExecutionContext implements JobExecutionContext {

    private final GithubContext github;
    private final EnvContext env;
    private final VarsContext vars;
    private final JobContext job;
    private final StepsContext steps;
    private final RunnerContext runner;
    private final SecretsContext secrets;
    private final StrategyContext strategy;
    @Nullable  private final MatrixContext matrix;
    private final NeedsContext needs;
    @Nullable private final InputsContext inputs;

    protected AbstractJobExecutionContext(JsonNode _root) {
        ObjectNode root = ContextUtilities.validateIsObjectNode(_root);

        github = GithubContext.ensure(root.get("github"));
        env = EnvContext.ensure(root.get("env"));
        vars = VarsContext.ensure(root.get("vars"));
        job = JobContext.ensure(root.get("job"));
        steps = StepsContext.ensure(root.get("steps"));
        runner = RunnerContext.ensure(root.get("runner"));
        secrets = SecretsContext.ensure(root.get("secrets"));
        strategy = StrategyContext.ensure(root.get("strategy"));
        matrix = MatrixContext.create(root.get("matrix")).orElse(null);
        needs = NeedsContext.ensure(root.get("needs"));
        inputs = InputsContext.create(root.get("inputs")).orElse(null);
    }
}
