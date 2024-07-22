package eu.venthe.pipeline.orchestrator.modules.automation.workflows.execution.context.job_execution.contexts;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.contexts.utilities.ContextUtilities;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.HashMap;
import java.util.Map;

import static eu.venthe.pipeline.orchestrator.utilities.CollectionUtilities.sameKey;
import static eu.venthe.pipeline.orchestrator.utilities.CollectionUtilities.toMap;

/**
 * The needs context contains outputs from all jobs that are defined as a direct dependency of the current job. Note
 * that this doesn't include implicitly dependent jobs (for example, dependent jobs of a dependent job). For more
 * information on defining job dependencies, see "Workflow syntax for GitHub Actions."
 */
@Getter
@EqualsAndHashCode
@RequiredArgsConstructor
public class NeedsContext {
    /**
     * This context is only populated for workflow runs that have dependent jobs, and changes for each job in a workflow
     * run. You can access this context from any job or step in a workflow. This object contains all the properties
     * listed below.
     */
    @JsonAnyGetter
    private final Map<String, JobNeed> jobs = new HashMap<>();

    @JsonCreator
    private NeedsContext(JsonNode _root) {
        ObjectNode root = ContextUtilities.validateIsObjectNode(_root);

        jobs.putAll(ContextUtilities.validateIsObjectNode(root).properties().stream()
                .map(sameKey(e -> ContextUtilities.ensure(e, JobNeed::new)))
                .collect(toMap()));
    }

    public static NeedsContext ensure(JsonNode jobs) {
        return ContextUtilities.ensure(jobs, NeedsContext::new);
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
