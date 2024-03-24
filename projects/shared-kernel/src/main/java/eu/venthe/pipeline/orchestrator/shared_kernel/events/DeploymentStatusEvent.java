package eu.venthe.pipeline.orchestrator.shared_kernel.events;

import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts.*;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.model.DeploymentStatusAction;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.model.EventType;
import lombok.Getter;
import lombok.ToString;

import java.time.OffsetDateTime;
import java.util.Optional;

/**
 * This event occurs when there is activity relating to deployment statuses. For more information, see "About deployments." For information about the APIs to manage deployments, see the GraphQL API documentation or "Deployments" in the REST API documentation.
 * <p>
 * For activity relating to deployment creation, use the deployment event.
 * <p>
 * To subscribe to this event, a GitHub App must have at least read-level access for the "Deployments" repository permission.
 */
@Getter
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class DeploymentStatusEvent extends AbstractProjectEvent {
    private final DeploymentStatusAction action;
    private final DeploymentContext deployment;
    private final DeploymentStatusContext deploymentStatus;
    private final Optional<WorkflowContext> workflow;
    private final Optional<WorkflowRunContext> workflowRun;

    protected DeploymentStatusEvent(ObjectNode root, OffsetDateTime timestamp) {
        super(root, EventType.DEPLOYMENT_STATUS, timestamp);

        action = DeploymentStatusActionContext.ensure(root.get("action"));
        deployment = DeploymentContext.ensure(root.get("deployment"));
        deploymentStatus = DeploymentStatusContext.ensure(root.get("deploymentStatus"));
        workflow = WorkflowContext.create(root.get("workflow"));
        workflowRun = WorkflowRunContext.create(root.get("workflowRun"));
    }
}
