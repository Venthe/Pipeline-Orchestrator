package eu.venthe.pipeline.orchestrator.shared_kernel.system_events;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.contexts.ReviewersContext;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.contexts.UserContext;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.contexts.WorkflowJobRunContext;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.contexts.WorkflowRunContext;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.contexts.common.DateTimeContext;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.contexts.model.DeploymentReviewActionContext;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.contexts.utilities.ContextUtilities;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.model.DeploymentReviewAction;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.model.EventType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

/**
 * This event occurs when there is activity relating to deployment reviews.
 * <p>
 * For activity relating to deployment creation or deployment status, use the deployment or deployment_status event.
 */
@Getter
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class DeploymentReviewEvent extends AbstractProjectEvent {
    private final DeploymentReviewAction action;
    private final OffsetDateTime since;
    private final Optional<WorkflowRunContext> workflowRun;
    private final Optional<UserContext> approver;
    private final Optional<String> comment;
    private final Optional<WorkflowJobRunContext> workflowJobRun;
    private final List<WorkflowJobRunContext> workflowJobRuns;
    private final List<ReviewersContext> reviewers;

    public DeploymentReviewEvent(ObjectNode _root) {
        super(_root);
        var root = ContextUtilities.validateIsObjectNode(_root);

        action = DeploymentReviewActionContext.ensure(root.get("action"));
        since = DateTimeContext.ensure(root.get("since"));
        workflowRun = WorkflowRunContext.create(root.get("workflowRun"));
        approver = UserContext.create(root.get("approver"));
        final JsonNode comment1 = root.get("comment");
        comment = ContextUtilities.Text.create(comment1);
        workflowJobRun = WorkflowJobRunContext.create(root.get("workflowJobRun"));
        workflowJobRuns = WorkflowJobRunContext.list(root.get("workflowJobRuns"));
        reviewers = ReviewersContext.list(root.get("reviewers"));
    }

    public EventType getType() {
        return EventType.DEPLOYMENT_REVIEW;
    }

}
