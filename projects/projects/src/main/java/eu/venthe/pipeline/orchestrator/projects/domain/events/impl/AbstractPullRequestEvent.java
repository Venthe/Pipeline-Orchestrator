package eu.venthe.pipeline.orchestrator.projects.domain.events.impl;

import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.pipeline.orchestrator.projects.domain.events.contexts.ActionContext;
import eu.venthe.pipeline.orchestrator.projects.domain.events.model.EventType;
import eu.venthe.pipeline.orchestrator.projects.domain.events.AbstractHandledEvent;
import eu.venthe.pipeline.orchestrator._archive2.workflows.contexts.on.OnTypes;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

// TODO: Describe properties for pull request
@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@Getter
public abstract class AbstractPullRequestEvent extends AbstractHandledEvent {
    private final ActionContext.Action action;

    protected AbstractPullRequestEvent(ObjectNode root, ActionContext.Action action) {
        super(root, EventType.PULL_REQUEST);

        this.action = ActionContext.ensure(this.root.get("action"));

        if (!this.action.equals(action)) {
            throw new IllegalArgumentException("Invalid action");
        }
    }

    @Override
    public Boolean matches(OnTypes onTypes) {
        return onTypes.match(this.action.getValue());
    }


}
