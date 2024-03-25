package eu.venthe.pipeline.orchestrator.shared_kernel.events;

import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts.DeploymentContext;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts.WorkflowJobContext;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.model.EventType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.OffsetDateTime;
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

    public WorkflowJobEvent(ObjectNode root) {
        super(root, EventType.WORKFLOW_JOB);

        deployment = DeploymentContext.create(root.get("deployment"));
        workflowJob = WorkflowJobContext.ensure(root.get("workflowJob"));
    }
}
