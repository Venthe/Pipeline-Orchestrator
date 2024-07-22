package eu.venthe.pipeline.orchestrator.modules.automation.workflows.definition.contexts.jobs;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.definition.contexts.PermissionsContext;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.contexts.utilities.ContextUtilities;

import java.util.Optional;
import java.util.Set;

import static eu.venthe.pipeline.orchestrator.modules.automation.workflows.Permissions.*;

public class JobContext {
//    private final Optional<String> name;
    private final Optional<PermissionsContext> permissions;


    private JobContext(JsonNode _root) {
        ObjectNode root = ContextUtilities.validateIsObjectNode(_root);

        permissions = PermissionsContext.create(_root.get("permissions"), Set.of(
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

    // name
    // permissions
    // needs
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
