package eu.venthe.pipeline.orchestrator.shared_kernel.job_execution;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.pipeline.orchestrator.shared_kernel.job_execution.contexts.*;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.contexts.utilities.ContextUtilities;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@ToString
@EqualsAndHashCode
@SuperBuilder
@SuppressWarnings("ALL")
public class ReusableWorkflowJobContext extends CommonJobContext implements JobExecutionContext {
    /**
     * Outputs for a reusable workflow
     */
    private final JobsContext jobs;

    public ReusableWorkflowJobContext(JsonNode _root) {
        super(_root);

        ObjectNode root = ContextUtilities.validateIsObjectNode(_root);
        jobs = JobsContext.ensure(root.get("jobs"));
    }
}
