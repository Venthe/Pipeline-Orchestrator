package eu.venthe.pipeline.shared_kernel.system_events.contexts;

import com.fasterxml.jackson.databind.JsonNode;
import eu.venthe.pipeline.shared_kernel.system_events.contexts.common.DateTimeContext;
import eu.venthe.pipeline.shared_kernel.system_events.contexts.common.PathContext;
import eu.venthe.pipeline.shared_kernel.system_events.contexts.common.UrlContext;
import eu.venthe.pipeline.shared_kernel.system_events.contexts.model.WorkflowStateContext;
import eu.venthe.pipeline.shared_kernel.system_events.contexts.utilities.ContextUtilities;
import eu.venthe.pipeline.shared_kernel.system_events.model.WorkflowState;

import java.net.URL;
import java.nio.file.Path;
import java.time.OffsetDateTime;
import java.util.Optional;

public class WorkflowContext {
    private final String id;
    private final String name;
    private final Path path;
    private final WorkflowState state;
    private final URL url;
    private final OffsetDateTime updatedAt;

    private WorkflowContext(final JsonNode _root) {
        final var root = ContextUtilities.validateIsObjectNode(_root);

        id = ContextUtilities.Text.ensure(root.get("id"));
        name = ContextUtilities.Text.ensure(root.get("name"));
        path = PathContext.ensure(root.get("path"));
        state = WorkflowStateContext.ensure(root.get("state"));
        url = UrlContext.ensure(root.get("url"));
        updatedAt = DateTimeContext.ensure(root.get("updatedAt"));

    }

    public static Optional<WorkflowContext> create(final JsonNode workflow) {
        return ContextUtilities.create(workflow, WorkflowContext::new);
    }

    public static WorkflowContext ensure(final JsonNode workflow) {
        return ContextUtilities.ensure(workflow, WorkflowContext::new);
    }
}
