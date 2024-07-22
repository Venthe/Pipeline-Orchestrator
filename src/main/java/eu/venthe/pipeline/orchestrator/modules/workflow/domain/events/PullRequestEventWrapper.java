package eu.venthe.pipeline.orchestrator.modules.workflow.domain.events;

import eu.venthe.pipeline.orchestrator.modules.automation.workflows.definition.contexts.on.matchers.OnActivityType;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.definition.contexts.on.matchers.OnBranches;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.PullRequestEvent;

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
