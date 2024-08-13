package eu.venthe.platform.workflow.events;

import eu.venthe.platform.workflow.definition.contexts.on.matchers.OnActivityType;
import eu.venthe.platform.workflow.definition.contexts.on.matchers.OnBranches;
import eu.venthe.platform.shared_kernel.system_events.PullRequestEvent;

// TODO: Matches
public class PullRequestEventWrapper extends AbstractEventWrapper<PullRequestEvent> {
    public PullRequestEventWrapper(PullRequestEvent event) {
        super(event);
    }

    @Override
    public Boolean matches(OnActivityType onTypes) {
        return onTypes.match(getEvent().getAction().toString());
    }

    @Override
    public Boolean matches(OnBranches onBranches) {
        return onBranches.match(getEvent().getPullRequest().getBase().getRef());
    }
}
