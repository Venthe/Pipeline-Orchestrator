package eu.venthe.pipeline.orchestrator.shared_kernel.events;

import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts.DeploymentContext;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts.WorkflowJobContext;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.model.EventType;
import lombok.Getter;
import lombok.ToString;

import java.time.ZonedDateTime;
import java.util.Optional;

@Getter
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class WorkflowJobEvent extends AbstractProjectEvent {
    /**
     * A request for a specific ref(branch,sha,tag) to be deployed
     */
    private final Optional<DeploymentContext> deployment;
    private final WorkflowJobContext workflowJob;

    protected WorkflowJobEvent(ObjectNode root, ZonedDateTime timestamp) {
        super(root, EventType.WORKFLOW_JOB, timestamp);

        deployment = DeploymentContext.create(root.get("deployment"));
        workflowJob = WorkflowJobContext.ensure(root.get("workflow_job"));
    }
}
