package eu.venthe.pipeline.orchestrator.shared_kernel.system_events;

import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.contexts.WorkflowContext;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.contexts.WorkflowRunContext;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.contexts.utilities.ContextUtilities;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.model.EventType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * This event occurs when there is activity relating to a run of a GitHub Actions workflow. For more information, see
 * "About workflows."
 * <p>
 * For activity relating to a job in a workflow run, use the workflow_job event.
 */
@Getter
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class WorkflowRunEvent extends AbstractProjectEvent {
    private final WorkflowContext workflow;
    private final WorkflowRunContext workflowRun;

    public WorkflowRunEvent(ObjectNode _root) {
        super(_root);

        var root = ContextUtilities.validateIsObjectNode(_root);

        workflow = WorkflowContext.ensure(root.get("workflow"));
        workflowRun = WorkflowRunContext.ensure(root.get("workflowRun"));
    }

    public EventType getType() {
        return EventType.WORKFLOW_RUN;
    }
}
