package eu.venthe.platform.shared_kernel.system_events;

import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.platform.shared_kernel.system_events.contexts.PullRequestContext;
import eu.venthe.platform.shared_kernel.system_events.contexts.PullRequestReviewActionContext;
import eu.venthe.platform.shared_kernel.system_events.contexts.PullRequestReviewContext;
import eu.venthe.platform.shared_kernel.system_events.contexts.utilities.ContextUtilities;
import eu.venthe.platform.shared_kernel.system_events.model.EventType;
import eu.venthe.platform.shared_kernel.system_events.model.PullRequestReviewAction;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * This event occurs when there is activity relating to a pull request review. A pull request review is a group of pull
 * request review comments in addition to a body comment and a state. For more information, see "About pull request
 * reviews."
 * <p>
 * For activity related to pull request review comments, pull request comments, or pull request review threads, use the
 * pull_request_review_comment, issue_comment, or pull_request_review_thread events instead.
 */
@Getter
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class PullRequestReviewEvent extends AbstractProjectEvent {
    private final PullRequestReviewAction action;
    private final PullRequestContext pullRequest;
    private final PullRequestReviewContext review;

    public PullRequestReviewEvent(ObjectNode _root) {
        super(_root);
        var root = ContextUtilities.validateIsObjectNode(_root);

        action = PullRequestReviewActionContext.ensure(root.get("action"));
        pullRequest = PullRequestContext.ensure(root.get("pullRequest"));
        review = PullRequestReviewContext.ensure(root.get("review"));
    }

    public EventType getType() {
        return EventType.PULL_REQUEST_REVIEW;
    }
}
