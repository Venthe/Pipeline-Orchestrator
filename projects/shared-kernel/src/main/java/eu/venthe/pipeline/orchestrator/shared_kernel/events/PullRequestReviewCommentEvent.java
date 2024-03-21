package eu.venthe.pipeline.orchestrator.shared_kernel.events;

import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts.CommentContext;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts.PullRequestContext;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts.PullRequestReviewCommentActionContext;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.model.EventType;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.model.PullRequestReviewCommentAction;
import lombok.Getter;
import lombok.ToString;

import java.time.ZonedDateTime;

/**
 * This event occurs when there is activity relating to a pull request review comment. A pull request review comment is a comment on a pull request's diff. For more information, see "Commenting on a pull request." For information about the APIs to manage pull request review comments, see the GraphQL API documentation or "Pull request review comments" in the REST API documentation.
 * <p>
 * For activity related to pull request reviews, pull request comments, or pull request review threads, use the pull_request_review, issue_comment, or pull_request_review_thread events instead.
 * <p>
 * To subscribe to this event, a GitHub App must have at least read-level access for the "Pull requests" repository permission.
 */
@Getter
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class PullRequestReviewCommentEvent extends AbstractProjectEvent {
    private final String comment;
    private final PullRequestContext pullRequest;
    private final PullRequestReviewCommentAction action;

    protected PullRequestReviewCommentEvent(ObjectNode root, ZonedDateTime timestamp) {
        super(root, EventType.PULL_REQUEST_REVIEW_COMMENT, timestamp);

        comment = CommentContext.ensure(root.get("comment"));
        pullRequest = PullRequestContext.ensure(root.get("pull_request"));
        action = PullRequestReviewCommentActionContext.ensure(root.get("action"));
    }
}
