package eu.venthe.pipeline.orchestrator.shared_kernel.job_execution.contexts;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.pipeline.orchestrator.shared_kernel.version_control_events.contexts.utilities.ContextUtilities;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Map;

import static eu.venthe.pipeline.orchestrator.utilities.CollectionUtilities.sameKey;
import static eu.venthe.pipeline.orchestrator.utilities.CollectionUtilities.toMap;

/**
 * The steps context contains information about the steps in the current job that have an id specified and have already
 * run.
 * <p>
 * This context changes for each step in a job. You can access this context from any step in a job. This object contains
 * all the properties listed below.
 */
@Getter
@ToString
@EqualsAndHashCode
public class StepsContext {
    public static Map<String, StepContext> ensure(JsonNode steps) {
        return ContextUtilities.validateIsObjectNode(steps).properties().stream()
                .map(sameKey(e -> ContextUtilities.ensure(e, StepContext::new)))
                .collect(toMap());
    }

    @Getter
    @ToString
    @EqualsAndHashCode
    @SuperBuilder
    public static class StepContext {
        /**
         * The set of outputs defined for the step. For more information, see "Metadata syntax for GitHub Actions."
         */
        @Singular
        private final Map<String, String> outputs;
        /**
         * The result of a completed step after continue-on-error is applied. Possible values are success, failure,
         * cancelled, or skipped. When a continue-on-error step fails, the outcome is failure, but the final conclusion
         * is success.
         */
        private final String conclusion;
        /**
         * The result of a completed step before continue-on-error is applied. Possible values are success, failure,
         * cancelled, or skipped. When a continue-on-error step fails, the outcome is failure, but the final conclusion
         * is success.
         */
        private final String outcome;

        protected StepContext(JsonNode _root) {
            ObjectNode root = ContextUtilities.validateIsObjectNode(_root);

            conclusion = ContextUtilities.Text.ensure(root.get("conclusion"));
            outcome = ContextUtilities.Text.ensure(root.get("outcome"));
            outputs = root.get("outputs").properties().stream()
                    .map(sameKey(ContextUtilities.Text::ensure))
                    .collect(toMap());
        }
    }
}
