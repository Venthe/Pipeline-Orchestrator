package eu.venthe.pipeline.orchestrator.shared_kernel.system_events.contexts;

import com.fasterxml.jackson.databind.JsonNode;
import eu.venthe.pipeline.orchestrator.projects.domain.ProjectId;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.contexts.common.UrlContext;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.contexts.model.RepositoryVisibilityContext;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.contexts.utilities.ContextUtilities;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.model.RepositoryVisibility;

import java.net.URL;
import java.util.Optional;

// TODO: Add URL for the teams responsible of the repository
// TODO: Add URL for the tags of the repository
// TODO: Add URL for the releases of the repository
// TODO: Add URL for the pull requests of the repository
// TODO: Add URL for the issues of the repository
// TODO: Add URL for the commits of the repository
// TODO: Add URL for the blobs of the repository
// TODO: Add URL for the deployments of the repository

/**
 * The repository on GitHub where the event occurred. Webhook payloads contain the repository property when the event
 * occurs from activity in a repository.
 */
@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class RepositoryContext {
    /**
     * Unique identifier of the repository
     */
    private final ProjectId id;
    /**
     * The name of the repository.
     */
    private final String name;
    private final String fullName;
    private final Optional<String> description;
    private final UserContext owner;
    private final URL url;
    /**
     * Whether the repository is private or public.
     */
    private final RepositoryVisibility visibility;

    private RepositoryContext(final JsonNode _root) {
        final var root = ContextUtilities.validateIsObjectNode(_root);

        this.id = ContextUtilities.ensure(root.get("id"), ContextUtilities.fromTextMapper(ProjectId::from));
        this.name = ContextUtilities.ensure(root.get("name"), ContextUtilities.toTextMapper());
        this.fullName = ContextUtilities.ensure(root.get("fullName"), ContextUtilities.toTextMapper());
        this.description = ContextUtilities.create(root.get("description"), ContextUtilities.toTextMapper());
        this.owner = UserContext.ensure(root.get("owner"));
        this.url = UrlContext.ensure(root.get("url"));
        this.visibility = RepositoryVisibilityContext.ensure(root.get("visibility"));
    }


    public static RepositoryContext ensure(final JsonNode root) {
        return ContextUtilities.ensure(root, RepositoryContext::new);
    }

    public static Optional<RepositoryContext> create(JsonNode root) {
        return ContextUtilities.create(root, RepositoryContext::new);
    }
}
