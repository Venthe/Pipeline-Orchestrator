package eu.venthe.platform.workflow.events;

import eu.venthe.platform.workflow.definition.contexts.on.matchers.OnBranches;
import eu.venthe.platform.workflow.definition.contexts.on.matchers.OnPaths;
import eu.venthe.platform.shared_kernel.system_events.PushEvent;

import java.nio.file.Path;
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
        Collection<String> changedFiles = getEvent().getCommits().stream()
                .flatMap(e -> Stream.of(
                        e.getAdded().stream(),
                        e.getModified().stream(),
                        e.getRemoved().stream()
                ))
                .flatMap(UnaryOperator.identity())
                .map(Path::toString)
                .collect(Collectors.toSet());
        return onPaths.match(changedFiles);
    }
}
