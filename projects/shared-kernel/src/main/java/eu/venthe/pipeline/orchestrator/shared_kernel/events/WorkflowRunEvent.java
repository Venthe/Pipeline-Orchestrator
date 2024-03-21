package eu.venthe.pipeline.orchestrator.shared_kernel.events;

import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.AbstractProjectEvent;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts.WorkflowContext;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts.WorkflowJobContext;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts.WorkflowRunContext;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.model.EventType;
import lombok.Getter;
import lombok.ToString;

import java.time.ZonedDateTime;

@Getter
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class WorkflowRunEvent extends AbstractProjectEvent {
    private final WorkflowContext workflow;
    private final WorkflowRunContext workflowRun;

    protected WorkflowRunEvent(ObjectNode root, ZonedDateTime timestamp) {
        super(root, EventType.WORKFLOW_RUN, timestamp);

        workflow = WorkflowContext.ensure(root.get("workflow"));
        workflowRun = WorkflowRunContext.ensure(root.get("workflow_run"));
    }
}
