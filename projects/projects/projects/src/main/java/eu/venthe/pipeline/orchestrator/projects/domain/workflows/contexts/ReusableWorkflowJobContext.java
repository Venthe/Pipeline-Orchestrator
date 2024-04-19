package eu.venthe.pipeline.orchestrator.projects.domain.workflows.contexts;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.pipeline.orchestrator.projects.domain.workflows.contexts.jobs.*;
import eu.venthe.pipeline.orchestrator.projects.domain.workflows.contexts.jobs.contexts.*;
import eu.venthe.pipeline.orchestrator.shared_kernel.version_control_events.contexts.utilities.ContextUtilities;

import java.util.Optional;

@SuppressWarnings("ALL")
public class ReusableWorkflowJobContext {
    private final String name;
    private final Optional<NeedsContext> needs;
    private final Optional<PermissionsContext> permissions;
    private final Optional<IfContext> _if;
    private final UsesContext uses;
    private final Optional<WithContext> with;
    private final Optional<SecretsContext> secrets;
    private final Optional<StrategyContext> strategy;
    private final Optional<ConcurrencyContext> concurrency;
    private final Optional<DefaultsContext> strategy;

    public ReusableWorkflowJobContext(String jobId, JsonNode _root) {
        ObjectNode root = ContextUtilities.validateIsObjectNode(_root);

        name = JobNameContext.create(root.get("name")).orElse(jobId);
        needs = NeedsContext.create(root.get("needs"));
        permissions = PermissionsContext.create(root.get("permissions"));
        _if = IfContext.create(root.get("if"));
        uses = UsesContext.ensure(root.get("uses"));
        with = WithContext.create(root.get("with"));
        secrets = SecretsContext.create(root.get("secrets"));
        strategy = StrategyContext.create(root.get("strategy"));
        concurrency = ConcurrencyContext.create(root.get("concurrency"));
    }
}
