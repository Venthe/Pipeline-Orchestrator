package eu.venthe.pipeline.orchestrator.shared_kernel.job_execution.contexts;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.pipeline.orchestrator.shared_kernel.version_control_events.contexts.utilities.ContextUtilities;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Singular;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.experimental.UtilityClass;

import java.util.Map;

import static eu.venthe.pipeline.orchestrator.utilities.CollectionUtilities.sameKey;
import static eu.venthe.pipeline.orchestrator.utilities.CollectionUtilities.toMap;

/**
 * The needs context contains outputs from all jobs that are defined as a direct dependency of the current job. Note
 * that this doesn't include implicitly dependent jobs (for example, dependent jobs of a dependent job). For more
 * information on defining job dependencies, see "Workflow syntax for GitHub Actions."
 */
@UtilityClass
public class NeedsContext {
    /**
     * This context is only populated for workflow runs that have dependent jobs, and changes for each job in a workflow
     * run. You can access this context from any job or step in a workflow. This object contains all the properties
     * listed below.
     */
    public static Map<String, JobNeed> ensure(JsonNode jobs) {
        return ContextUtilities.validateIsObjectNode(jobs).properties().stream()
                .map(sameKey(e -> ContextUtilities.ensure(e, JobNeed::new)))
                .collect(toMap());
    }

    /**
     * A single job that the current job depends on.
     */
    @Getter
    @ToString
    @EqualsAndHashCode
    @SuperBuilder
    public static class JobNeed {
        /**
         * The set of outputs of a job that the current job depends on.
         */
        @Singular
        private final Map<String, String> outputs;
        /**
         * The result of a job that the current job depends on. Possible values are success, failure, cancelled, or
         * skipped.
         */
        private final String result;

        private JobNeed(JsonNode _root) {
            ObjectNode root = ContextUtilities.validateIsObjectNode(_root);

            result = ContextUtilities.Text.ensure(root.get("result"));
            outputs = root.get("outputs").properties().stream()
                    .map(sameKey(ContextUtilities.Text::ensure))
                    .collect(toMap());
        }
    }
}
