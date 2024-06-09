package eu.venthe.pipeline.orchestrator.workflow_executions.domain.job_executions.domain.job_execution;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.pipeline.orchestrator.workflow_executions.domain.job_executions.domain.job_execution.contexts.JobsContext;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.contexts.utilities.ContextUtilities;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@SuppressWarnings("ALL")
public class ReusableWorkflowJobExecutionContext extends CommonJobExecutionContext implements JobExecutionContext {
    /**
     * Outputs for a reusable workflow
     */
    private final JobsContext jobs;

    public ReusableWorkflowJobExecutionContext(JsonNode _root) {
        super(_root);

        ObjectNode root = ContextUtilities.validateIsObjectNode(_root);
        jobs = JobsContext.ensure(root.get("jobs"));
    }
}
