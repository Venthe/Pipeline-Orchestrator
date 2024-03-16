package eu.venthe.pipeline.orchestrator.projects.domain.events.contexts;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.pipeline.orchestrator.projects.domain.events.contexts.common.CommitShaContext;
import eu.venthe.pipeline.orchestrator.projects.domain.events.contexts.common.DateTimeContext;
import eu.venthe.pipeline.orchestrator.projects.domain.events.contexts.definitions.Commit;
import eu.venthe.pipeline.orchestrator.projects.domain.events.contexts.definitions.Committer;
import eu.venthe.pipeline.orchestrator._archive2.utilities.ContextUtilities;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class CommitContext implements Commit {
    private final ObjectNode root;

    @Getter
    private final String message;
    @Getter
    private final String sha;
    @Getter
    private final OffsetDateTime timestamp;
    @Getter
    private final Committer author;
    @Getter
    private final Committer committer;
    @Getter
    private final Set<String> added;
    @Getter
    private final Set<String> modified;
    @Getter
    private final Set<String> removed;

    private CommitContext(ObjectNode root) {
        this.root = root;

        message = ContextUtilities.toTextual(this.root.get("message")).orElseThrow();
        sha = CommitShaContext.ensure(this.root.get("sha"));
        timestamp = DateTimeContext.ensure(this.root.get("timestamp"));
        author = CommitterContext.ensure(this.root.get("author"));
        committer = CommitterContext.ensure(this.root.get("committer"));
        added = ContextUtilities.ensureStringCollection(this.root.get("added"), Collectors.toSet());
        modified = ContextUtilities.ensureStringCollection(this.root.get("modified"), Collectors.toSet());
        removed = ContextUtilities.ensureStringCollection(this.root.get("removed"), Collectors.toSet());
    }

    public static Optional<Commit> create(JsonNode root) {
        return ContextUtilities.get(CommitContext::new, root);
    }

    public static Commit ensure(JsonNode root) {
        return create(root).orElseThrow(() -> new IllegalArgumentException("Head commit must be present"));
    }
}
