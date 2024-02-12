package eu.venthe.pipeline.orchestrator.events.impl.pull_request;

import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.pipeline.orchestrator.events.contexts.ActionContext;
import eu.venthe.pipeline.orchestrator.events.impl.AbstractPullRequestEvent;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@Getter
public class PullRequestReadyForReviewEvent extends AbstractPullRequestEvent {
    protected PullRequestReadyForReviewEvent(ObjectNode root) {
        super(root, ActionContext.Action.READY_FOR_REVIEW);
    }
}
