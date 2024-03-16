package eu.venthe.pipeline.orchestrator.projects.domain.events.impl;

import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.pipeline.orchestrator.projects.domain.events.AbstractHandledEvent;
import eu.venthe.pipeline.orchestrator.projects.domain.events.contexts.CommitContext;
import eu.venthe.pipeline.orchestrator.projects.domain.events.contexts.CommitsContext;
import eu.venthe.pipeline.orchestrator.projects.domain.events.contexts.CommitterContext;
import eu.venthe.pipeline.orchestrator.projects.domain.events.contexts.RepositoryContext;
import eu.venthe.pipeline.orchestrator.projects.domain.events.contexts.common.CommitShaContext;
import eu.venthe.pipeline.orchestrator.projects.domain.events.contexts.common.RefContext;
import eu.venthe.pipeline.orchestrator.projects.domain.events.contexts.definitions.Commit;
import eu.venthe.pipeline.orchestrator.projects.domain.events.contexts.definitions.Committer;
import eu.venthe.pipeline.orchestrator.projects.domain.events.contexts.definitions.Repository;
import eu.venthe.pipeline.orchestrator.projects.domain.events.model.EventType;
import eu.venthe.pipeline.orchestrator.projects.domain.workflows.contexts.on.OnBranches;
import eu.venthe.pipeline.orchestrator.projects.domain.workflows.contexts.on.OnPaths;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// TODO: Describe properties for push
@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@Getter
public class PushEvent extends AbstractHandledEvent {
    // private final Optional<EnterpriseContext> enterprise;
    // private final Optional<Organization> organization;
    // private final Optional<InstallationLite> installation;
    private final String ref;
    /**
     * The SHA of the most recent commit on `ref` before the push.
     */
    private final String before;
    /**
     * The SHA of the most recent commit on `ref` after the push.
     */
    private final String after;
    // /** Whether this push created the `ref`. */
    // private final Boolean created;
    // /** Whether this push deleted the `ref`. */
    // private final Boolean deleted;
    // /** Whether this push was a force push of the `ref`. */
    // private final Boolean forced;
    private final Optional<String> baseRef;
    // /** URL that shows the changes in this `ref` update, from the `before` commit to the `after` commit. For a newly created `ref` that is directly based on the default branch, this is the comparison between the head of the default branch and the `after` commit. Otherwise, this shows all commits until the `after` commit. */
    // private final String compare ;
    /**
     * An array of commit objects describing the pushed commits. (Pushed commits are all commits that are included in the `compare` between the `before` commit and the `after` commit.) The array includes a maximum of 20 commits. If necessary, you can use the [Commits API](https://docs.github.com/en/rest/reference/repos#commits) to fetch additional commits. This limit is applied to timeline events only and isn't applied to webhook deliveries.
     */
    private final Set<Commit> commits;
    /**
     * For pushes where `after` is or points to a commit object, an expanded representation of that commit. For pushes where `after` refers to an annotated tag object, an expanded representation of the commit pointed to by the annotated tag.
     */
    private final Optional<Commit> headCommit;
    private final Repository repository;
    private final Committer pusher;
    private final Committer sender;

    public PushEvent(ObjectNode root) {
        super(root, EventType.PUSH);

        ref = RefContext.ensure(this.root.get("ref"));
        before = CommitShaContext.ensure(this.root.get("before"));
        after = CommitShaContext.ensure(this.root.get("after"));
        pusher = CommitterContext.ensure(this.root.get("pusher"));
        sender = CommitterContext.ensure(this.root.get("sender"));
        headCommit = CommitContext.create(this.root.get("headCommit"));
        baseRef = RefContext.create(this.root.get("baseRed"));
        repository = RepositoryContext.ensure(root.get("repository"));
        commits = CommitsContext.ensure(root.get("commits"));
    }

    @Override
    public Boolean matches(OnBranches onBranches) {
        return onBranches.match(getRef());
    }

    @Override
    public Boolean matches(OnPaths onPaths) {
        return onPaths.match(
                commits.stream()
                        .flatMap(e -> Stream.concat(
                                e.getAdded().stream(),
                                Stream.concat(
                                        e.getModified().stream(),
                                        e.getRemoved().stream()
                                )
                        ))
                        .collect(Collectors.toSet())
        );
    }
}
