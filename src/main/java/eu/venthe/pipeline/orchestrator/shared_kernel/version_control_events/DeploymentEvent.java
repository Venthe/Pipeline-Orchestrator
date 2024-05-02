package eu.venthe.pipeline.orchestrator.shared_kernel.version_control_events;

import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.pipeline.orchestrator.shared_kernel.version_control_events.contexts.model.DeploymentActionContext;
import eu.venthe.pipeline.orchestrator.shared_kernel.version_control_events.contexts.DeploymentContext;
import eu.venthe.pipeline.orchestrator.shared_kernel.version_control_events.contexts.WorkflowContext;
import eu.venthe.pipeline.orchestrator.shared_kernel.version_control_events.contexts.WorkflowRunContext;
import eu.venthe.pipeline.orchestrator.shared_kernel.version_control_events.model.DeploymentAction;
import eu.venthe.pipeline.orchestrator.shared_kernel.version_control_events.model.EventType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Optional;

/**
 * This event occurs when there is activity relating to deployments.
 * For more information, see "About deployments."
 * <p>
 * For activity relating to deployment status, use the deployment_status event.
 */
@Getter
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class DeploymentEvent extends AbstractProjectEvent {
    private final DeploymentAction action;
    private final DeploymentContext deployment;
    private final Optional<WorkflowContext> workflow;
    private final Optional<WorkflowRunContext> workflowRun;

    public DeploymentEvent(ObjectNode root) {
        super(root, EventType.DEPLOYMENT);

        action = DeploymentActionContext.ensure(root.get("action"));
        deployment = DeploymentContext.ensure(root.get("deployment"));
        workflow = WorkflowContext.create(root.get("workflow"));
        workflowRun = WorkflowRunContext.create(root.get("workflowRun"));
    }
}
