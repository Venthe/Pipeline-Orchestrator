package eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts;

import com.fasterxml.jackson.databind.JsonNode;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts.common.DateTimeContext;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts.common.UrlContext;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts.git.ActorContext;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts.git.GitHashContext;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts.git.CommitMessageContext;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts.common.PathContext;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts.utilities.ContextUtilities;
import eu.venthe.pipeline.orchestrator.shared_kernel.git.GitHash;
import lombok.Getter;

import java.net.URL;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Getter
// components/commit-details.yaml#
public class CommitDetailsContext {
    private final ActorContext author;
    private final ActorContext committer;

    /**
     * An array of files added in the commit.
     */
    private final List<String> added;

    /**
     * An array of files modified by the commit.
     */
    private final List<String> modified;

    /**
     * An array of files removed in the commit.
     */
    private final List<String> removed;

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

    private final GitHash treeId;

    /**
     * URL that points to the commit API resource.
     */
    private final URL url;

    private CommitDetailsContext(final JsonNode _root) {
        final var root = ContextUtilities.validateIsObjectNode(_root);

        author = ActorContext.ensure(root.get("author"));
        committer = ActorContext.ensure(root.get("committer"));
        distinct = CommitDetailsIsDistinctContext.ensure(root.get("distinct"));
        id = CommitDetailsIdContext.ensure(root.get("id"));
        message = CommitMessageContext.ensure(root.get("message"));
        added = PathContext.list(root.get("added"));
        modified = PathContext.list(root.get("modified"));
        removed = PathContext.list(root.get("removed"));
        timestamp = DateTimeContext.ensure(root.get("timestamp"));
        treeId = GitHashContext.ensure(root.get("tree_id"));
        url = UrlContext.ensure(root.get("url"));
    }

    public static List<CommitDetailsContext> list(JsonNode commits) {
        if (!commits.isArray()) {
            throw new IllegalArgumentException();
        }

        return StreamSupport.stream(commits.spliterator(), false)
                .map(CommitDetailsContext::new)
                .toList();
    }

    public static Optional<CommitDetailsContext> create(final JsonNode headCommit) {
        return ContextUtilities.create(headCommit, CommitDetailsContext::new);
    }
}
