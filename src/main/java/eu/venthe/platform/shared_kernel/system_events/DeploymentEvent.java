package eu.venthe.platform.shared_kernel.system_events;

import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.platform.shared_kernel.system_events.contexts.DeploymentContext;
import eu.venthe.platform.shared_kernel.system_events.contexts.WorkflowContext;
import eu.venthe.platform.shared_kernel.system_events.contexts.WorkflowRunContext;
import eu.venthe.platform.shared_kernel.system_events.contexts.model.DeploymentActionContext;
import eu.venthe.platform.shared_kernel.system_events.contexts.utilities.ContextUtilities;
import eu.venthe.platform.shared_kernel.system_events.model.DeploymentAction;
import eu.venthe.platform.shared_kernel.system_events.model.EventType;
import eu.venthe.platform.workflow.runs.dependencies.TimeService;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Optional;

/**
 * This event occurs when there is activity relating to deployments. For more information, see "About deployments."
 * <p>
 * For activity relating to deployment status, use the deployment_status event.
 */
@Getter
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class DeploymentEvent extends AbstractRepositoryEvent {
    private final DeploymentAction action;
    private final DeploymentContext deployment;
    private final Optional<WorkflowContext> workflow;
    private final Optional<WorkflowRunContext> workflowRun;

    public DeploymentEvent(ObjectNode _root, TimeService timeService) {
        super(_root, timeService);
        var root = ContextUtilities.validateIsObjectNode(_root);

        action = DeploymentActionContext.ensure(root.get("action"));
        deployment = DeploymentContext.ensure(root.get("deployment"));
        workflow = WorkflowContext.create(root.get("workflow"));
        workflowRun = WorkflowRunContext.create(root.get("workflowRun"));
    }

    public EventType getType() {
        return EventType.DEPLOYMENT;
    }
}
