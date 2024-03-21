package eu.venthe.pipeline.orchestrator.projects.domain.events.contexts;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.pipeline.orchestrator.projects.domain.events.contexts.common.DateTimeContext;
import eu.venthe.pipeline.orchestrator.projects.domain.events.contexts.definitions.Repository;
import eu.venthe.pipeline.orchestrator.projects.domain.utilities.ContextUtilities;
import lombok.Getter;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.Optional;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
@Getter
public class RepositoryContext implements Repository {
    private final ObjectNode root;

    private final String provider;
    private final String name;
    private final OffsetDateTime createdAt;
    private final OffsetDateTime updatedAt;
    private final Optional<OffsetDateTime> pushedAt;
    private final URI gitUrl;
    private final String sshUrl;
    private final URI cloneUrl;
    private final String defaultBranch;

    public RepositoryContext(ObjectNode root) {
        this.root = root;

        provider = ContextUtilities.toTextual(this.root.get("provider")).orElseThrow();
        name = ContextUtilities.toTextual(this.root.get("name")).orElseThrow();
        createdAt = DateTimeContext.ensure(this.root.get("createdAt"));
        updatedAt = DateTimeContext.ensure(this.root.get("updatedAt"));
        pushedAt = DateTimeContext.create(this.root.get("pushedAt"));
        gitUrl = ContextUtilities.toTextual(this.root.get("gitUrl")).map(URI::create).orElseThrow();
        sshUrl = ContextUtilities.toTextual(this.root.get("sshUrl")).orElseThrow();
        cloneUrl = ContextUtilities.toTextual(this.root.get("cloneUrl")).map(URI::create).orElseThrow();
        defaultBranch = ContextUtilities.toTextual(this.root.get("defaultBranch")).orElseThrow(() -> new IllegalArgumentException("Default branch must be present"));
    }

    public static Optional<Repository> create(JsonNode root) {
        return ContextUtilities.get(RepositoryContext::new, root);
    }

    public static Repository ensure(JsonNode root) {
        return create(root).orElseThrow(() -> new IllegalArgumentException("Repository must be present"));
    }
}
