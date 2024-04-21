package eu.venthe.pipeline.orchestrator.shared_kernel.job_execution.contexts;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;

import java.util.Map;

/**
 * The needs context contains outputs from all jobs that are defined as a direct dependency of the current job. Note
 * that this doesn't include implicitly dependent jobs (for example, dependent jobs of a dependent job). For more
 * information on defining job dependencies, see "Workflow syntax for GitHub Actions."
 */
@AllArgsConstructor
public class NeedsContext {
    /**
     * This context is only populated for workflow runs that have dependent jobs, and changes for each job in a workflow
     * run. You can access this context from any job or step in a workflow. This object contains all the properties
     * listed below.
     */
    private final Map<String, JobNeed> needs;

    public static NeedsContext ensure(JsonNode needs) {
        throw new UnsupportedOperationException();
    }

    /**
     * A single job that the current job depends on.
     */
    @AllArgsConstructor
    public static class JobNeed {
        /**
         * The set of outputs of a job that the current job depends on.
         */
        private final Map<String, String> outputs;
        /**
         * The result of a job that the current job depends on. Possible values are success, failure, cancelled, or
         * skipped.
         */
        private final String result;
    }
}
