package eu.venthe.platform.shared_kernel.system_events.contexts;

import com.fasterxml.jackson.databind.JsonNode;
import eu.venthe.platform.shared_kernel.system_events.contexts.common.BooleanContext;
import eu.venthe.platform.shared_kernel.system_events.contexts.common.DateTimeContext;
import eu.venthe.platform.shared_kernel.system_events.contexts.common.PathContext;
import eu.venthe.platform.shared_kernel.system_events.contexts.common.UrlContext;
import eu.venthe.platform.shared_kernel.system_events.contexts.git.ActorContext;
import eu.venthe.platform.shared_kernel.system_events.contexts.git.CommitMessageContext;
import eu.venthe.platform.shared_kernel.system_events.contexts.git.GitHashContext;
import eu.venthe.platform.shared_kernel.system_events.contexts.utilities.ContextUtilities;
import lombok.Getter;

import java.net.URL;
import java.nio.file.Path;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Getter
public class CommitDetailsContext {
    private final ActorContext author;
    private final ActorContext committer;

    /**
     * An array of files added in the commit.
     */
    private final List<Path> added;

    /**
     * An array of files modified by the commit.
     */
    private final List<Path> modified;

    /**
     * An array of files removed in the commit.
     */
    private final List<Path> removed;

    /**
     * Whether this commit is distinct from any that have been pushed before.
     */
    private final Boolean distinct;

    private final String id;

    private final String message;

    /**
     * The ISO 8601 timestamp of the commit.
     */
    private final OffsetDateTime timestamp;

    private final String treeId;

    /**
     * URL that points to the commit API resource.
     */
    private final URL url;

    private CommitDetailsContext(final JsonNode _root) {
        final var root = ContextUtilities.validateIsObjectNode(_root);

        author = ActorContext.ensure(root.get("author"));
        committer = ActorContext.ensure(root.get("committer"));
        final JsonNode distinct1 = root.get("distinct");
        distinct = BooleanContext.ensure(distinct1);
        final JsonNode id1 = root.get("id");
        id = ContextUtilities.Text.ensure(id1);
        message = CommitMessageContext.ensure(root.get("message"));
        added = PathContext.list(root.get("added"));
        modified = PathContext.list(root.get("modified"));
        removed = PathContext.list(root.get("removed"));
        timestamp = DateTimeContext.ensure(root.get("timestamp"));
        treeId = GitHashContext.ensure(root.get("tree_id"));
        url = UrlContext.ensure(root.get("url"));
    }

    public static List<CommitDetailsContext> list(JsonNode commits) {
        return ContextUtilities.Collection.createCollection(commits, stream -> stream
                .map(CommitDetailsContext::new)
                .toList());
    }

    public static Optional<CommitDetailsContext> create(final JsonNode headCommit) {
        return ContextUtilities.create(headCommit, CommitDetailsContext::new);
    }
}
