package eu.venthe.pipeline.orchestrator.shared_kernel.system_events.contexts;

import com.fasterxml.jackson.databind.JsonNode;
import eu.venthe.pipeline.orchestrator.shared_kernel.git.Revision;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.contexts.common.BooleanContext;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.contexts.common.DateTimeContext;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.contexts.git.GitHashContext;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.contexts.git.RevisionContext;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.contexts.utilities.ContextUtilities;

import java.time.OffsetDateTime;
import java.util.Optional;

// TODO: Implement context
@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class DeploymentContext {
    /**
     * Unique identifier of the deployment
     */
    private final String id;
    private final String sha;
    private final Revision ref;
    /**
     * The name of the task for the deployment (e.g., deploy or deploy:migrations).
     */
    // FIXME: What is the purpose of this field?
    private final Optional<String> task;
    private final OffsetDateTime createdAt;
    private final OffsetDateTime updatedAt;
    /**
     * Specifies if the given environment is will no longer exist at some point in the future.
     */
    private final Boolean transientEnvironment;
    /**
     * Specifies if the given environment is one that end-users directly interact with.
     */
    private final Boolean productionEnvironment;

    private DeploymentContext(final JsonNode _root) {
        final var root = ContextUtilities.validateIsObjectNode(_root);

        this.id = ContextUtilities.Text.ensure(root.get("id"));
        this.sha = GitHashContext.ensure(root.get("sha"));
        this.ref = RevisionContext.ensure(root.get("ref"));
        this.task = ContextUtilities.Text.create(root.get("task"));
        this.createdAt = DateTimeContext.ensure(root.get("createdAt"));
        this.updatedAt = DateTimeContext.ensure(root.get("updatedAt"));
        this.transientEnvironment = BooleanContext.create(root.get("transientEnvironment")).orElse(false);
        this.productionEnvironment = BooleanContext.create(root.get("productionEnvironment")).orElse(false);
    }


    public static DeploymentContext ensure(final JsonNode deployment) {
        return ContextUtilities.ensure(deployment, DeploymentContext::new);
    }

    public static Optional<DeploymentContext> create(final JsonNode deployment) {
        return ContextUtilities.create(deployment, DeploymentContext::new);
    }
}
