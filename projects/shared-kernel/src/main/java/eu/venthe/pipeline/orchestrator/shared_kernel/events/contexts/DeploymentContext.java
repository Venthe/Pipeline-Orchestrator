package eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts.common.BooleanContext;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts.common.DateTimeContext;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts.git.CommitHashContext;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts.git.ReferenceContext;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts.utilities.ContextUtilities;
import eu.venthe.pipeline.orchestrator.shared_kernel.git.GitHash;

import java.time.OffsetDateTime;
import java.util.Optional;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class DeploymentContext {
    /**
     * Unique identifier of the deployment
     */
    private final String id;
    private final GitHash sha;
    private final String ref;
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

    protected DeploymentContext(JsonNode _root) {
        if (!_root.isObject()) {
            throw new IllegalArgumentException();
        }

        var root = (ObjectNode) _root;

        this.id = ContextUtilities.ensureText(root.get("id"));
        this.sha = CommitHashContext.ensure(root.get("sha"));
        this.ref = ReferenceContext.ensure(root.get("ref"));
        this.task = ContextUtilities.createText(root.get("task"));
        this.createdAt = DateTimeContext.ensure(root.get("createdAt"));
        this.updatedAt = DateTimeContext.ensure(root.get("updatedAt"));
        this.transientEnvironment = BooleanContext.create(root.get("transientEnvironment")).orElse(false);
        this.productionEnvironment = BooleanContext.create(root.get("productionEnvironment")).orElse(false);
    }


    public static DeploymentContext ensure(JsonNode deployment) {
        return ContextUtilities.ensure(deployment, DeploymentContext::new);
    }

    public static Optional<DeploymentContext> create(JsonNode deployment) {
        return ContextUtilities.create(deployment, DeploymentContext::new);
    }
}
