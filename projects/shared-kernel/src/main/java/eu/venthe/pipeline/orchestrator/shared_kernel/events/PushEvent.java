package eu.venthe.pipeline.orchestrator.shared_kernel.events;

import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts.*;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts.git.CommitHashContext;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts.git.ReferenceNameContext;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts.common.UrlContext;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.model.EventType;
import eu.venthe.pipeline.orchestrator.shared_kernel.git.GitHash;
import eu.venthe.pipeline.orchestrator.shared_kernel.git.GitReference;
import lombok.Getter;
import lombok.ToString;

import java.net.URL;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

/**
 * This event occurs when there is a push to a repository branch. This includes when a commit is pushed, when a commit tag is pushed, when a branch is deleted, when a tag is deleted, or when a repository is created from a template. To subscribe to only branch and tag deletions, use the delete webhook event.
 * <p>
 * To subscribe to this event, a GitHub App must have at least read-level access for the "Contents" repository permission.
 * <p>
 * Note: An event will not be created when more than three tags are pushed at once.
 */
@Getter
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class PushEvent extends AbstractProjectEvent {
    /**
     * The SHA of the most recent commit on ref after the push.
     */
    private final GitHash after;
    /**
     * The SHA of the most recent commit on ref before the push.
     */
    private final GitHash before;
    private final Optional<GitReference.Name> baseRef;
    private final List<CommitDetailsContext> commits;
    /**
     * URL that shows the changes in this ref update, from the before commit to the after commit. For a newly created ref that is directly based on the default branch, this is the comparison between the head of the default branch and the after commit. Otherwise, this shows all commits until the after commit.
     */
    private final URL compare;
    /**
     * Whether this push created the ref.
     */
    private final Boolean created;
    /**
     * Whether this push deleted the ref.
     */
    private final Boolean deleted;
    /**
     * Whether this push was a force push of the ref.
     */
    private final Boolean forced;
    private final Optional<CommitDetailsContext> headCommit;
    private final UserContext pusher;
    /**
     * The full git ref that was pushed. Example: refs/heads/main or refs/tags/v3.14.1.
     */
    private final GitReference.Name ref;

    protected PushEvent(ObjectNode root, OffsetDateTime timestamp) {
        super(root, EventType.PUSH, timestamp);

        after = CommitHashContext.ensure(root.get("after"));
        before = CommitHashContext.ensure(root.get("before"));
        baseRef = ReferenceNameContext.create(root.get("baseRef"));
        commits = CommitDetailsContext.list(root.get("commits"));
        compare = UrlContext.ensure(root.get("compare"));
        created = PushIsCreatingRefContext.ensure(root.get("created"));
        deleted = PushIsDeletingRefContext.ensure(root.get("deleted"));
        forced = PushIsForcedContext.ensure(root.get("forced"));
        headCommit = CommitDetailsContext.create(root.get("headCommit"));
        pusher = UserContext.ensure(root.get("pusher"));
        ref = ReferenceNameContext.ensure(root.get("ref"));
    }
}
