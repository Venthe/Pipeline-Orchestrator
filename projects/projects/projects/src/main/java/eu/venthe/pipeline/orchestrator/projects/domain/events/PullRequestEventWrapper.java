package eu.venthe.pipeline.orchestrator.projects.domain.events;

import eu.venthe.pipeline.orchestrator.projects.domain.workflows.contexts.on.matchers.on.OnBranches;
import eu.venthe.pipeline.orchestrator.projects.domain.workflows.contexts.on.matchers.on.OnTypes;
import eu.venthe.pipeline.orchestrator.shared_kernel.version_control_events.PullRequestEvent;

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
