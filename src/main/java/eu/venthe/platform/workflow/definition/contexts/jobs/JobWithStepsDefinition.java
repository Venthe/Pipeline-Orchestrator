package eu.venthe.platform.workflow.definition.contexts.jobs;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.platform.shared_kernel.system_events.contexts.utilities.ContextUtilities;
import eu.venthe.platform.workflow.definition.contexts.JobName;
import eu.venthe.platform.workflow.definition.contexts.WorkflowDefinitionPermissionsContext;
import eu.venthe.platform.workflow.definition.contexts.jobs.steps.AbstractStepDefinition;
import eu.venthe.platform.workflow.definition.contexts.jobs.steps.WorkflowDefinitionStepsContext;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.Collections;
import java.util.Set;

import static eu.venthe.platform.workflow.model.Permissions.*;

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
@ToString
@RequiredArgsConstructor
public class JobWithStepsDefinition {
    @Nullable
    private final WorkflowDefinitionPermissionsContext permissions;
    @Nullable
    private final WorkflowDefinitionNeedsContext needs;
    private final WorkflowDefinitionStepsContext steps;

    private JobWithStepsDefinition(JsonNode _root) {
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

    public static JobWithStepsDefinition ensure(final JsonNode value) {
        return ContextUtilities.ensure(value, JobWithStepsDefinition::new, () -> new IllegalArgumentException("Job must exist"));
    }

    public Set<JobName> getNeeds() {
        return needs == null ? Collections.emptySet() : needs.getNeeds();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        @Nullable
        private WorkflowDefinitionPermissionsContext permissions;
        @Nullable
        private WorkflowDefinitionNeedsContext needs;
        private final WorkflowDefinitionStepsContext.WorkflowDefinitionStepsContextBuilder stepsBuilder = WorkflowDefinitionStepsContext.builder();

        public JobWithStepsDefinition build() {
            return new JobWithStepsDefinition(permissions, needs, stepsBuilder.build());
        }

        public <T extends AbstractStepDefinition> Builder addStep(T step) {
            stepsBuilder.step(step);

            return this;
        }
    }
}
