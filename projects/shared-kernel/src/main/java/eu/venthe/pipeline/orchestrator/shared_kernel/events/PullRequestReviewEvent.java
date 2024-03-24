package eu.venthe.pipeline.orchestrator.shared_kernel.events;

import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts.PullRequestContext;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts.PullRequestReviewActionContext;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts.PullRequestReviewContext;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.model.EventType;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.model.PullRequestReviewAction;
import lombok.Getter;
import lombok.ToString;

import java.time.OffsetDateTime;

/**
 * This event occurs when there is activity relating to a pull request review. A pull request review is a group of pull
 * request review comments in addition to a body comment and a state. For more information, see "About pull request reviews."
 * <p>
 * For activity related to pull request review comments, pull request comments, or pull request review threads, use
 * the pull_request_review_comment, issue_comment, or pull_request_review_thread events instead.
 */
@Getter
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class PullRequestReviewEvent extends AbstractProjectEvent {
    private final PullRequestReviewAction action;
    private final PullRequestContext pullRequest;
    private final PullRequestReviewContext review;

    protected PullRequestReviewEvent(ObjectNode root, OffsetDateTime timestamp) {
        super(root, EventType.PULL_REQUEST_REVIEW, timestamp);

        action = PullRequestReviewActionContext.ensure(root.get("action"));
        pullRequest = PullRequestContext.ensure(root.get("pullRequest"));
        review = PullRequestReviewContext.ensure(root.get("review"));
    }
}
