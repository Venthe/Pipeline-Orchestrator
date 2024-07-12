package eu.venthe.pipeline.orchestrator.shared_kernel.system_events;

import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.contexts.PullRequestContext;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.contexts.PullRequestNumberContext;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.contexts.model.PullRequestActionContext;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.contexts.utilities.ContextUtilities;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.model.EventType;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.model.PullRequestAction;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * This event occurs when there is activity on a pull request. For more information, see "About pull requests."
 * <p>
 * For activity related to pull request reviews, pull request review comments, pull request comments, or pull request
 * review threads, use the pull_request_review, pull_request_review_comment, issue_comment, or
 * pull_request_review_thread events instead.
 */
@Getter
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class PullRequestEvent extends AbstractProjectEvent {
    private final Integer number;
    private final PullRequestAction action;
    private final PullRequestContext pullRequest;

    public PullRequestEvent(ObjectNode _root) {
        super(_root);

        var root = ContextUtilities.validateIsObjectNode(_root);

        action = PullRequestActionContext.ensure(root.get("action"));
        number = PullRequestNumberContext.ensure(root.get("number"));
        pullRequest = PullRequestContext.ensure(root.get("pullRequest"));
    }

    public EventType getType() {
        return EventType.PULL_REQUEST;
    }
}
