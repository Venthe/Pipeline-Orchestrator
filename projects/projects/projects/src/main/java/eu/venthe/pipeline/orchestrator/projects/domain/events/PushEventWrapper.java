package eu.venthe.pipeline.orchestrator.projects.domain.events;

import eu.venthe.pipeline.orchestrator.projects.api.PushEvent;
import eu.venthe.pipeline.orchestrator.projects.domain.workflows.contexts.on.OnBranches;
import eu.venthe.pipeline.orchestrator.projects.domain.workflows.contexts.on.OnPaths;

import java.util.Collection;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// TODO: Matches
public class PushEventWrapper extends AbstractEventWrapper<PushEvent> {
    public PushEventWrapper(PushEvent event) {
        super(event);
    }

    @Override
    public Boolean matches(OnBranches onBranches) {
        return onBranches.match(getEvent().getRef());
    }

    @Override
    public Boolean matches(OnPaths onPaths) {
        return onPaths.match(getEvent().getCommits().stream()
                .flatMap(e -> Stream.of(
                        e.getAdded().stream().flatMap(Collection::stream),
                        e.getModified().stream().flatMap(Collection::stream),
                        e.getRemoved().stream().flatMap(Collection::stream)
                ))
                .flatMap(UnaryOperator.identity())
                .collect(Collectors.toSet()));
    }
}
