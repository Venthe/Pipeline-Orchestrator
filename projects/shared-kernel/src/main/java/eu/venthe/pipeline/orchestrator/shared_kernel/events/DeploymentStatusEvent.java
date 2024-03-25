package eu.venthe.pipeline.orchestrator.shared_kernel.events;

import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts.*;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts.model.DeploymentStatusActionContext;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.model.DeploymentStatusAction;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.model.EventType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Optional;

/**
 * This event occurs when there is activity relating to deployment statuses. For more information, see "About deployments."
 * <p>
 * For activity relating to deployment creation, use the deployment event.
 */
@Getter
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class DeploymentStatusEvent extends AbstractProjectEvent {
    private final DeploymentStatusAction action;
    private final DeploymentContext deployment;
    private final DeploymentStatusContext deploymentStatus;
    private final Optional<WorkflowContext> workflow;
    private final Optional<WorkflowRunContext> workflowRun;

    public DeploymentStatusEvent(ObjectNode root) {
        super(root, EventType.DEPLOYMENT_STATUS);

        action = DeploymentStatusActionContext.ensure(root.get("action"));
        deployment = DeploymentContext.ensure(root.get("deployment"));
        deploymentStatus = DeploymentStatusContext.ensure(root.get("deploymentStatus"));
        workflow = WorkflowContext.create(root.get("workflow"));
        workflowRun = WorkflowRunContext.create(root.get("workflowRun"));
    }
}
