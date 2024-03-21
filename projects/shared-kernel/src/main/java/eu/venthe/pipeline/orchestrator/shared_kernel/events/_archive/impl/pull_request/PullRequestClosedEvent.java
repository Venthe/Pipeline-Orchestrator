package eu.venthe.pipeline.orchestrator.shared_kernel.events._archive.impl.pull_request;

import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.pipeline.orchestrator.projects.domain.events.contexts.ActionContext;
import eu.venthe.pipeline.orchestrator.shared_kernel.events._archive.impl.AbstractPullRequestEvent;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@Getter
public class PullRequestClosedEvent extends AbstractPullRequestEvent {
    protected PullRequestClosedEvent(ObjectNode root) {
        super(root, ActionContext.Action.CLOSED);
    }
}
