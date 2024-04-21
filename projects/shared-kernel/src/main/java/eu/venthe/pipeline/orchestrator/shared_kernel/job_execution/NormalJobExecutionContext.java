package eu.venthe.pipeline.orchestrator.shared_kernel.job_execution;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.pipeline.orchestrator.shared_kernel.job_execution.contexts.*;
import eu.venthe.pipeline.orchestrator.shared_kernel.version_control_events.contexts.utilities.ContextUtilities;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.Optional;

@Getter
@ToString
@EqualsAndHashCode
@SuperBuilder
@SuppressWarnings("ALL")
public class NormalJobExecutionContext implements JobContext_ {
    private final GithubContext github;
    private final EnvContext env;
    private final VarsContext vars;
    private final JobContext job;
    private final StepsContext steps;
    private final RunnerContext runner;
    private final SecretsContext secrets;
    private final StrategyContext strategy;
    private final Optional<MatrixContext> matrix;
    private final NeedsContext needs;
    private final Optional<InputsContext> inputs;

    public NormalJobExecutionContext(JsonNode _root) {
        ObjectNode root = ContextUtilities.validateIsObjectNode(_root);

        github = GithubContext.ensure(root.get("github"));
        env = EnvContext.ensure(root.get("env"));
        vars = VarsContext.ensure(root.get("vars"));
        job = JobContext.ensure(root.get("job"));
        steps = StepsContext.ensure(root.get("steps"));
        runner = RunnerContext.ensure(root.get("runner"));
        secrets = SecretsContext.ensure(root.get("secrets"));
        strategy = StrategyContext.ensure(root.get("strategy"));
        matrix = MatrixContext.create(root.get("matrix"));
        needs = NeedsContext.ensure(root.get("needs"));
        inputs = InputsContext.create(root.get("inputs"));
    }
}
