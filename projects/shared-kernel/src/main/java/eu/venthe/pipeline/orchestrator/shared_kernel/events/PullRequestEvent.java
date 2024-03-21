package eu.venthe.pipeline.orchestrator.shared_kernel.events;

import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts.PullRequestActionContext;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts.PullRequestContext;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts.PullRequestNumberContext;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.model.EventType;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.model.PullRequestAction;
import lombok.Getter;
import lombok.ToString;

import java.time.OffsetDateTime;

/**
 * This event occurs when there is activity on a pull request. For more information, see "About pull requests." For information about the APIs to manage pull requests, see the GraphQL API documentation or "Pulls" in the REST API documentation.
 * <p>
 * For activity related to pull request reviews, pull request review comments, pull request comments, or pull request review threads, use the pull_request_review, pull_request_review_comment, issue_comment, or pull_request_review_thread events instead.
 * <p>
 * To subscribe to this event, a GitHub App must have at least read-level access for the "Pull requests" repository permission.
 */
@Getter
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class PullRequestEvent extends AbstractProjectEvent {
    private final Integer number;
    private final PullRequestAction action;
    private final PullRequestContext pullRequest;

    protected PullRequestEvent(ObjectNode root, OffsetDateTime timestamp) {
        super(root, EventType.PULL_REQUEST, timestamp);

        action = PullRequestActionContext.ensure(root.get("action"));
        number = PullRequestNumberContext.ensure(root.get("number"));
        pullRequest = PullRequestContext.ensure(root.get("pull_request"));
    }
}
