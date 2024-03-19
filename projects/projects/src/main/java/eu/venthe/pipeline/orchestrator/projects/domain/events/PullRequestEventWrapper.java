package eu.venthe.pipeline.orchestrator.projects.domain.events;

import eu.venthe.pipeline.orchestrator.projects.api.PullRequestEvent;
import eu.venthe.pipeline.orchestrator.projects.domain.workflows.contexts.on.OnBranches;
import eu.venthe.pipeline.orchestrator.projects.domain.workflows.contexts.on.OnTypes;

// TODO: Matches
public class PullRequestEventWrapper extends AbstractEventWrapper<PullRequestEvent> {
    public PullRequestEventWrapper(PullRequestEvent event) {
        super(event);
    }

    @Override
    public Boolean matches(OnTypes onTypes) {
        return onTypes.match(getEvent().getAction().toString());
    }

    @Override
    public Boolean matches(OnBranches onBranches) {
        return onBranches.match(getEvent().getPullRequest().getBase().getRef());
    }
}
