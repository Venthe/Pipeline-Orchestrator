package eu.venthe.pipeline.orchestrator.events.contexts;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.pipeline.orchestrator.utilities.ContextUtilities;
import lombok.Getter;

import java.util.Optional;

public final class ProjectContext {
    private final ObjectNode root;
    @Getter
    private final String name;
    @Getter
    private final String ref;
    @Getter
    private final String backendId;
    @Getter
    private final String sha;

    private ProjectContext(ObjectNode root) {
        this.root = root;
        name = ContextUtilities.toTextual(this.root.get("name"))
                .orElseThrow(() -> new IllegalArgumentException("Name of the project should exist"));
        backendId = ContextUtilities.toTextual(this.root.get("backendId"))
                .orElseThrow(() -> new IllegalArgumentException("Backend ID should be defined"));
        ref = ContextUtilities.toTextual(this.root.get("ref"))
                .orElseThrow(() -> new IllegalArgumentException("Ref should be defined"));
        sha = ContextUtilities.toTextual(this.root.get("sha"))
                .orElseThrow(() -> new IllegalArgumentException("SHA of the commit should be defined"));
    }

    public static Optional<ProjectContext> create(JsonNode root) {
        return ContextUtilities.get(ProjectContext::new, root);
    }

    public static ProjectContext ensure(JsonNode root) {
        return create(root).orElseThrow(() -> new IllegalArgumentException("Project must be present"));
    }

}
