package eu.venthe.pipeline.orchestrator.shared_kernel.events;

import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts.WorkflowDispatchInputsContext;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts.git.ReferenceContext;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts.common.PathContext;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.model.EventType;
import lombok.Getter;
import lombok.ToString;

import java.nio.file.Path;
import java.time.OffsetDateTime;

/**
 * This event occurs when a GitHub Actions workflow is manually triggered. For more information, see "Manually running a workflow."
 * <p>
 * For activity relating to workflow runs, use the workflow_run event.
 */
@Getter
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class WorkflowDispatchEvent extends AbstractProjectEvent {
    private final WorkflowDispatchInputsContext inputs;
    private final String ref;
    private final Path workflow;

    protected WorkflowDispatchEvent(ObjectNode root, OffsetDateTime timestamp) {
        super(root, EventType.WORKFLOW_DISPATCH, timestamp);

        inputs = WorkflowDispatchInputsContext.create(root.get("inputs"));
        ref = ReferenceContext.ensure(root.get("ref"));
        workflow = PathContext.ensure(root.get("workflow"));
    }
}
