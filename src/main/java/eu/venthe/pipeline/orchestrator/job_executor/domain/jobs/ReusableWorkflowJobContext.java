package eu.venthe.pipeline.orchestrator.job_executor.domain.jobs;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.pipeline.orchestrator.job_executor.domain.jobs.context.SecretsContext;
import eu.venthe.pipeline.orchestrator.job_executor.domain.jobs.context.StrategyContext;
import eu.venthe.pipeline.orchestrator.job_executor.domain.jobs.context.UsesContext;
import eu.venthe.pipeline.orchestrator.job_executor.domain.jobs.context.WithContext;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.contexts.utilities.ContextUtilities;

import java.util.Optional;

@SuppressWarnings("ALL")
public class ReusableWorkflowJobContext extends CommonJobContext {
    private final UsesContext uses;
    private final Optional<WithContext> with;
    private final Optional<SecretsContext> secrets;
    private final Optional<StrategyContext> strategy;

    public ReusableWorkflowJobContext(String jobId, JsonNode _root) {
        super(jobId, _root);
        ObjectNode root = ContextUtilities.validateIsObjectNode(_root);

        uses = UsesContext.ensure(root.get("uses"));
        with = WithContext.create(root.get("with"));
        secrets = SecretsContext.create(root.get("secrets"));
        strategy = StrategyContext.create(root.get("strategy"));
    }
}
