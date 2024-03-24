package eu.venthe.pipeline.orchestrator.shared_kernel.events;

import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts.*;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.model.DeploymentReviewAction;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.model.EventType;
import lombok.Getter;
import lombok.ToString;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

/**
 * This event occurs when there is activity relating to deployment reviews. For more information, see "About deployments." For information about the APIs to manage deployments, see the GraphQL API documentation or "Deployments" in the REST API documentation.
 * <p>
 * For activity relating to deployment creation or deployment status, use the deployment or deployment_status event.
 * <p>
 * To subscribe to this event, a GitHub App must have at least read-level access for the "Deployments" repository permission.
 */
@Getter
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class DeploymentReviewEvent extends AbstractProjectEvent {
    private final DeploymentReviewAction action;
    private final String since;
    private final Optional<WorkflowRunContext> workflowRun;
    private final Optional<UserContext> approver;
    private final Optional<String> comment;
    private final Optional<WorkflowJobRunContext> workflowJobRun;
    private final List<WorkflowJobRunContext> workflowJobRuns;
    private final List<ReviewersContext> reviewers;

    protected DeploymentReviewEvent(ObjectNode root, OffsetDateTime timestamp) {
        super(root, EventType.DEPLOYMENT_REVIEW, timestamp);

        action = DeploymentReviewActionContext.ensure(root.get("action"));
        since = DeploymentReviewSinceContext.ensure(root.get("since"));
        workflowRun = WorkflowRunContext.create(root.get("workflow_run"));
        approver = UserContext.create(root.get("approver"));
        comment = DeploymentReviewCommentContext.create(root.get("comment"));
        workflowJobRun = WorkflowJobRunContext.create(root.get("workflowJobRun"));
        workflowJobRuns = WorkflowJobRunContext.list(root.get("workflowJobRuns"));
        reviewers = ReviewersContext.list(root.get("reviewers"));
    }

}
