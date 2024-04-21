package eu.venthe.pipeline.orchestrator.shared_kernel.job_execution.contexts;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;

/**
 * For workflows with a matrix, the strategy context contains information about the matrix execution strategy for the
 * current job.
 * <p>
 * This context changes for each job in a workflow run. You can access this context from any job or step in a workflow.
 * This object contains all the properties listed below.
 */
@AllArgsConstructor
public class StrategyContext {
    /**
     * When this evaluates to true, all in-progress jobs are canceled if any job in a matrix fails. For more
     * information, see "Workflow syntax for GitHub Actions."
     */
    private final Boolean failFast;
    /**
     * The index of the current job in the matrix. Note: This number is a zero-based number. The first job's index in
     * the matrix is 0.
     */
    private final Number jobIndex;
    /**
     * The total number of jobs in the matrix. Note: This number is not a zero-based number. For example, for a matrix
     * with four jobs, the value of job-total is 4.
     */
    private final Number jobTotal;
    /**
     * The maximum number of jobs that can run simultaneously when using a matrix job strategy. For more information,
     * see "Workflow syntax for GitHub Actions."
     */
    private final Number maxParallel;

    public static StrategyContext ensure(JsonNode strategy) {
        throw new UnsupportedOperationException();
    }
}
