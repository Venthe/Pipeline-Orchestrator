package eu.venthe.pipeline.orchestrator.shared_kernel.system_events;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.pipeline.orchestrator.shared_kernel.git.Revision;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.contexts.CommitDetailsContext;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.contexts.UserContext;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.contexts.common.BooleanContext;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.contexts.common.UrlContext;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.contexts.git.GitHashContext;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.contexts.git.RevisionContext;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.contexts.utilities.ContextUtilities;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.model.EventType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.net.URL;
import java.util.List;
import java.util.Optional;

/**
 * This event occurs when there is a push to a repository branch. This includes when a commit is pushed, when a commit
 * tag is pushed, when a branch is deleted, when a tag is deleted, or when a repository is created from a template.
 * To subscribe to only branch and tag deletions, use the delete webhook event.
 */
@Getter
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class PushEvent extends AbstractProjectEvent {
    /**
     * The SHA of the most recent commit on ref after the push.
     */
    private final String after;
    /**
     * The SHA of the most recent commit on ref before the push.
     */
    private final String before;
    private final Optional<Revision> baseRef;
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
    private final Revision ref;

    public PushEvent(ObjectNode _root) {
        super(_root, EventType.PUSH);
        var root = ContextUtilities.validateIsObjectNode(_root);

        after = GitHashContext.ensure(root.get("after"));
        before = GitHashContext.ensure(root.get("before"));
        baseRef = RevisionContext.create(root.get("baseRef"));
        commits = CommitDetailsContext.list(root.get("commits"));
        compare = UrlContext.ensure(root.get("compare"));
        final JsonNode forced1 = root.get("created");
        created = BooleanContext.ensure(forced1);
        final JsonNode forced2 = root.get("deleted");
        deleted = BooleanContext.ensure(forced2);
        final JsonNode forced3 = root.get("forced");
        forced = BooleanContext.ensure(forced3);
        headCommit = CommitDetailsContext.create(root.get("headCommit"));
        pusher = UserContext.ensure(root.get("pusher"));
        ref = RevisionContext.ensure(root.get("ref"));
    }
}
