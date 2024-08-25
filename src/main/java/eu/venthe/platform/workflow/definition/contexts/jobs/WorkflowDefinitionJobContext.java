package eu.venthe.platform.workflow.definition.contexts.jobs;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.platform.workflow.definition.contexts.JobName;
import eu.venthe.platform.workflow.definition.contexts.WorkflowDefinitionPermissionsContext;
import eu.venthe.platform.workflow.definition.contexts.jobs.steps.WorkflowDefinitionStepsContext;
import eu.venthe.platform.shared_kernel.system_events.contexts.utilities.ContextUtilities;
import jakarta.annotation.Nullable;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.Collections;
import java.util.Set;

import static eu.venthe.platform.workflow.model.Permissions.*;

@ToString
@SuperBuilder
public class WorkflowDefinitionJobContext {
    // private final Optional<String> name;
    @Nullable
    private final WorkflowDefinitionPermissionsContext permissions;
    @Nullable
    private final WorkflowDefinitionNeedsContext needs;
    @Nullable
    private final WorkflowDefinitionStepsContext steps;

    private WorkflowDefinitionJobContext(JsonNode _root) {
        ObjectNode root = ContextUtilities.validateIsObjectNode(_root);

        needs = WorkflowDefinitionNeedsContext.create(root.get("needs")).orElse(null);
        permissions = WorkflowDefinitionPermissionsContext.create(root.get("permissions"), Set.of(
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
        )).orElse(null);

        steps = WorkflowDefinitionStepsContext.create(root.get("steps")).orElse(null);
    }

    public static WorkflowDefinitionJobContext ensure(final JsonNode value) {
        return ContextUtilities.ensure(value, WorkflowDefinitionJobContext::new, () -> new IllegalArgumentException("Job must exist"));
    }

    public Set<JobName> getNeeds() {
        return needs == null ? Collections.emptySet() : needs.getNeeds();
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
