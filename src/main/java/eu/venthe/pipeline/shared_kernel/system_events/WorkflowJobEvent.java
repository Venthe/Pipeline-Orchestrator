package eu.venthe.pipeline.shared_kernel.system_events;

import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.pipeline.shared_kernel.system_events.contexts.DeploymentContext;
import eu.venthe.pipeline.shared_kernel.system_events.contexts.WorkflowJobContext;
import eu.venthe.pipeline.shared_kernel.system_events.contexts.utilities.ContextUtilities;
import eu.venthe.pipeline.shared_kernel.system_events.model.EventType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Optional;

@Getter
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class WorkflowJobEvent extends AbstractProjectEvent {
    /**
     * A request for a specific ref(branch,sha,tag) to be deployed
     */
    private final Optional<DeploymentContext> deployment;
    private final WorkflowJobContext workflowJob;

    public WorkflowJobEvent(ObjectNode _root) {
        super(_root);
        var root = ContextUtilities.validateIsObjectNode(_root);

        deployment = DeploymentContext.create(root.get("deployment"));
        workflowJob = WorkflowJobContext.ensure(root.get("workflowJob"));
    }

    public EventType getType() {
        return EventType.WORKFLOW_JOB;
    }
}
