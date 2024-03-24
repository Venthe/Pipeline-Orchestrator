package eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts.common.DateTimeContext;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts.common.PathContext;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts.common.UrlContext;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts.utilities.ContextUtilities;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.model.WorkflowState;

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

    private WorkflowContext(JsonNode _root) {
        if (!_root.isObject()) {
            throw new IllegalArgumentException();
        }

        var root = (ObjectNode) _root;

        id = ContextUtilities.ensureText(root.get("id"));
        name = ContextUtilities.ensureText(root.get("name"));
        path = PathContext.ensure(root.get("path"));
        state = WorkflowStateContext.ensure(root.get("state"));
        url = UrlContext.ensure(root.get("url"));
        updatedAt = DateTimeContext.ensure(root.get("updatedAt"));

    }

    public static Optional<WorkflowContext> create(JsonNode workflow) {
        return ContextUtilities.create(workflow, WorkflowContext::new);
    }

    public static WorkflowContext ensure(JsonNode workflow) {
        return ContextUtilities.ensure(workflow, WorkflowContext::new);
    }
}
