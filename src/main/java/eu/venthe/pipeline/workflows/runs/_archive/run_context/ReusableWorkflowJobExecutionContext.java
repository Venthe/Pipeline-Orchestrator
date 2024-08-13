package eu.venthe.pipeline.workflows.runs._archive.run_context;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.pipeline.workflows.runs._archive.run_context.contexts.JobsContext;
import eu.venthe.pipeline.shared_kernel.system_events.contexts.utilities.ContextUtilities;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@SuppressWarnings("ALL")
public class ReusableWorkflowJobExecutionContext extends AbstractJobExecutionContext implements JobExecutionContext {
    /**
     * Outputs for a reusable workflow
     * */
    private final JobsContext jobs;

    public ReusableWorkflowJobExecutionContext(JsonNode _root) {
        super(_root);

        ObjectNode root = ContextUtilities.validateIsObjectNode(_root);
        jobs = JobsContext.ensure(root.get("jobs"));
    }
}
