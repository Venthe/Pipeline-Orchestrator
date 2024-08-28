package eu.venthe.platform.shared_kernel.system_events;

import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.platform.shared_kernel.system_events.contexts.DeploymentContext;
import eu.venthe.platform.shared_kernel.system_events.contexts.DeploymentStatusContext;
import eu.venthe.platform.shared_kernel.system_events.contexts.WorkflowContext;
import eu.venthe.platform.shared_kernel.system_events.contexts.WorkflowRunContext;
import eu.venthe.platform.shared_kernel.system_events.contexts.model.DeploymentStatusActionContext;
import eu.venthe.platform.shared_kernel.system_events.contexts.utilities.ContextUtilities;
import eu.venthe.platform.shared_kernel.system_events.model.DeploymentStatusAction;
import eu.venthe.platform.shared_kernel.system_events.model.EventType;
import eu.venthe.platform.workflow.runs.dependencies.TimeService;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Optional;

/**
 * This event occurs when there is activity relating to deployment statuses. For more information, see "About
 * deployments."
 * <p>
 * For activity relating to deployment creation, use the deployment event.
 */
@Getter
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class DeploymentStatusEvent extends AbstractRepositoryEvent {
    private final DeploymentStatusAction action;
    private final DeploymentContext deployment;
    private final DeploymentStatusContext deploymentStatus;
    private final Optional<WorkflowContext> workflow;
    private final Optional<WorkflowRunContext> workflowRun;

    public DeploymentStatusEvent(ObjectNode _root, TimeService timeService) {
        super(_root, timeService);
        var root = ContextUtilities.validateIsObjectNode(_root);

        action = DeploymentStatusActionContext.ensure(root.get("action"));
        deployment = DeploymentContext.ensure(root.get("deployment"));
        deploymentStatus = DeploymentStatusContext.ensure(root.get("deploymentStatus"));
        workflow = WorkflowContext.create(root.get("workflow"));
        workflowRun = WorkflowRunContext.create(root.get("workflowRun"));
    }

    public EventType getType() {
        return EventType.DEPLOYMENT_STATUS;
    }
}
