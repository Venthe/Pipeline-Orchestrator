package eu.venthe.pipeline.orchestrator.modules.automation.workflows.definition.contexts.jobs;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.definition.contexts.JobId;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.definition.contexts.PermissionsContext;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.contexts.utilities.ContextUtilities;
import lombok.ToString;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import static eu.venthe.pipeline.orchestrator.modules.automation.workflows.model.Permissions.*;

@ToString
public class JobContext {
    //    private final Optional<String> name;
    private final Optional<PermissionsContext> permissions;
    private final Optional<NeedsContext> needs;

    private JobContext(JsonNode _root) {
        ObjectNode root = ContextUtilities.validateIsObjectNode(_root);

        needs = NeedsContext.create(root.get("needs"));
        permissions = PermissionsContext.create(root.get("permissions"), Set.of(
                ACTIONS,
                ATTESTATIONS,
                CHECKS,
                CONTENTS,
                DEPLOYMENTS,
                DISCUSSIONS,
                ID_TOKEN,
                ISSUES,
                PACKAGES,
                PAGES,
                PULL_REQUESTS,
                REPOSITORY_PROJECTS,
                SECURITY_EVENTS,
                STATUSES
        ));
    }

    public static JobContext ensure(final JsonNode value) {
        return ContextUtilities.ensure(value, JobContext::new, () -> new IllegalArgumentException("Job must exist"));
    }

    public Set<JobId> getNeeds() {
        return needs.map(NeedsContext::getNeeds)
                .orElseGet(Collections::emptySet);
    }

    // name
    // permissions
    // if
    // runs-on
    // environment
    // concurrency
    // outputs
    // env
    // defaults
    // defaults.run
    // defaults.run.shell
    // defaults.run.working-directory
    // steps
    // timeout-minutes
    // strategy
    // strategy.matrix
    // strategy.matrix.include
    // strategy.matrix.exclude
    // strategy.fail-fast
    // strategy.max-parallel
    // continue-on-error
    // container
    // services
    // uses
    // with
    // secrets
}
