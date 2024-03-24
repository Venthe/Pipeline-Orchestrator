package eu.venthe.pipeline.orchestrator.shared_kernel.events;

import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts.WorkflowContext;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts.WorkflowRunContext;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.model.EventType;
import lombok.Getter;
import lombok.ToString;

import java.time.OffsetDateTime;

/**
 * This event occurs when there is activity relating to a run of a GitHub Actions workflow. For more information, see "About workflows." For information about the APIs to manage workflow runs, see the GraphQL documentation or "Workflow runs" in the REST API documentation.
 * <p>
 * For activity relating to a job in a workflow run, use the workflow_job event.
 */
@Getter
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class WorkflowRunEvent extends AbstractProjectEvent {
    private final WorkflowContext workflow;
    private final WorkflowRunContext workflowRun;

    protected WorkflowRunEvent(ObjectNode root, OffsetDateTime timestamp) {
        super(root, EventType.WORKFLOW_RUN, timestamp);

        workflow = WorkflowContext.ensure(root.get("workflow"));
        workflowRun = WorkflowRunContext.ensure(root.get("workflowRun"));
    }
}
