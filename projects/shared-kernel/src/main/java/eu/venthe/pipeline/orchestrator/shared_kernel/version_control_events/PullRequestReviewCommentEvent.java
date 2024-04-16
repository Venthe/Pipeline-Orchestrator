package eu.venthe.pipeline.orchestrator.shared_kernel.version_control_events;

import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.pipeline.orchestrator.shared_kernel.version_control_events.contexts.PullRequestReviewCommentContext;
import eu.venthe.pipeline.orchestrator.shared_kernel.version_control_events.contexts.PullRequestContext;
import eu.venthe.pipeline.orchestrator.shared_kernel.version_control_events.contexts.PullRequestReviewCommentActionContext;
import eu.venthe.pipeline.orchestrator.shared_kernel.version_control_events.model.EventType;
import eu.venthe.pipeline.orchestrator.shared_kernel.version_control_events.model.PullRequestReviewCommentAction;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * This event occurs when there is activity relating to a pull request review comment. A pull request review comment is
 * a comment on a pull request's diff. For more information, see "Commenting on a pull request."
 * <p>
 * For activity related to pull request reviews, pull request comments, or pull request review threads, use the pull_request_review, issue_comment, or pull_request_review_thread events instead.
 */
@Getter
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class PullRequestReviewCommentEvent extends AbstractProjectEvent {
    private final PullRequestReviewCommentContext comment;
    private final PullRequestContext pullRequest;
    private final PullRequestReviewCommentAction action;

    public PullRequestReviewCommentEvent(ObjectNode root) {
        super(root, EventType.PULL_REQUEST_REVIEW_COMMENT);

        comment = PullRequestReviewCommentContext.ensure(root.get("comment"));
        pullRequest = PullRequestContext.ensure(root.get("pullRequest"));
        action = PullRequestReviewCommentActionContext.ensure(root.get("action"));
    }
}
