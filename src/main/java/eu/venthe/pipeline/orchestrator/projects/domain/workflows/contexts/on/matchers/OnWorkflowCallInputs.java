package eu.venthe.pipeline.orchestrator.projects.domain.workflows.contexts.on.matchers;

import com.fasterxml.jackson.databind.JsonNode;
import eu.venthe.pipeline.orchestrator.shared_kernel.version_control_events.contexts.utilities.ContextUtilities;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

// TODO: Implement context
@RequiredArgsConstructor
public class OnWorkflowCallInputs {
    private final JsonNode root;

    public static Optional<OnWorkflowDispatchInputs> create(JsonNode root) {
        JsonNode root1 = root.get("inputs");

        if (root1.isEmpty() || root1.isMissingNode() || root1.isNull()) {
            return Optional.empty();
        }

        return ContextUtilities.create(root1, OnWorkflowDispatchInputs::new);
    }
}
