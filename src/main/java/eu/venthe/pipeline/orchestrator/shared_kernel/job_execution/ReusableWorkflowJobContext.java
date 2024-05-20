package eu.venthe.pipeline.orchestrator.shared_kernel.job_execution;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.pipeline.orchestrator.shared_kernel.job_execution.contexts.*;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.contexts.utilities.ContextUtilities;
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
public class ReusableWorkflowJobContext implements JobContext_ {
    private final GithubContext github;
    private final EnvContext env;
    private final VarsContext vars;
    private final JobContext job;
    /**
     * The jobs context is only available in reusable workflows, and can only be used to set outputs for a reusable
     * workflow. For more information, see "Reusing workflows."
     * <p>
     * This is only available in reusable workflows, and can only be used to set outputs for a reusable workflow. This
     * object contains all the properties listed below.
     */
    private final JobsContext jobs;
    private final StepsContext steps;
    private final RunnerContext runner;
    private final SecretsContext secrets;
    private final StrategyContext strategy;
    private final Optional<MatrixContext> matrix;
    private final NeedsContext needs;
    private final Optional<InputsContext> inputs;

    public ReusableWorkflowJobContext(JsonNode _root) {
        ObjectNode root = ContextUtilities.validateIsObjectNode(_root);

        github = GithubContext.ensure(root.get("github"));
        env = EnvContext.ensure(root.get("env"));
        vars = VarsContext.ensure(root.get("vars"));
        job = JobContext.ensure(root.get("job"));
        jobs = JobsContext.ensure(root.get("jobs"));
        steps = StepsContext.ensure(root.get("steps"));
        runner = RunnerContext.ensure(root.get("runner"));
        secrets = SecretsContext.ensure(root.get("secrets"));
        strategy = StrategyContext.ensure(root.get("strategy"));
        matrix = MatrixContext.create(root.get("matrix"));
        needs = NeedsContext.ensure(root.get("needs"));
        inputs = InputsContext.create(root.get("inputs"));
    }
}
