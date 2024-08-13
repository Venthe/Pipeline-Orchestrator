package eu.venthe.pipeline.shared_kernel.system_events.contexts;

import com.fasterxml.jackson.databind.JsonNode;
import eu.venthe.pipeline.shared_kernel.system_events.contexts.common.UrlContext;
import eu.venthe.pipeline.shared_kernel.system_events.contexts.utilities.ContextUtilities;

import java.net.URL;
import java.util.Optional;

// TODO: Finish the workflow run context
public class WorkflowRunContext {
    private final UserContext actor;
    private final URL artifactsUrl;
    private final URL cancelUrl;

    private WorkflowRunContext(final JsonNode _root) {
        final var root = ContextUtilities.validateIsObjectNode(_root);

        actor = UserContext.ensure(root.get("actor"));
        artifactsUrl = UrlContext.ensure(root.get("artifactsUrl"));
        cancelUrl = UrlContext.ensure(root.get("cancelUrl"));

        throw new UnsupportedOperationException();
    }


    public static Optional<WorkflowRunContext> create(final JsonNode workflowRun) {
        return ContextUtilities.create(workflowRun, WorkflowRunContext::new);
    }

    public static WorkflowRunContext ensure(final JsonNode workflowRun) {
        return ContextUtilities.ensure(workflowRun, WorkflowRunContext::new);
    }
}
