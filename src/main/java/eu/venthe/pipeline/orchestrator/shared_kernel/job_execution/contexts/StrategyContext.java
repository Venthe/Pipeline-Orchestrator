package eu.venthe.pipeline.orchestrator.shared_kernel.job_execution.contexts;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.pipeline.orchestrator.shared_kernel.version_control_events.contexts.utilities.ContextUtilities;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * For workflows with a matrix, the strategy context contains information about the matrix execution strategy for the
 * current job.
 * <p>
 * This context changes for each job in a workflow run. You can access this context from any job or step in a workflow.
 * This object contains all the properties listed below.
 */
@Getter
@ToString
@EqualsAndHashCode
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
    private final Integer jobIndex;
    /**
     * The total number of jobs in the matrix. Note: This number is not a zero-based number. For example, for a matrix
     * with four jobs, the value of job-total is 4.
     */
    private final Integer jobTotal;
    /**
     * The maximum number of jobs that can run simultaneously when using a matrix job strategy. For more information,
     * see "Workflow syntax for GitHub Actions."
     */
    private final Integer maxParallel;

    @JsonCreator
    private StrategyContext(JsonNode _root) {
        ObjectNode root = ContextUtilities.validateIsObjectNode(_root);

        failFast = ContextUtilities.ensure(root.get("fail_fast"), JsonNode::asBoolean);
        jobIndex = ContextUtilities.ensure(root.get("job_index"), JsonNode::asInt);
        jobTotal = ContextUtilities.ensure(root.get("job_total"), JsonNode::asInt);
        maxParallel = ContextUtilities.ensure(root.get("max_parallel"), JsonNode::asInt);
    }

    public static StrategyContext ensure(JsonNode strategy) {
        return ContextUtilities.ensure(strategy, StrategyContext::new);
    }
}
