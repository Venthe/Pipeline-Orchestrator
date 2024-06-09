package eu.venthe.pipeline.orchestrator.organizations.domain.projects.domain.workflows.contexts;

import com.fasterxml.jackson.databind.JsonNode;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.contexts.utilities.ContextUtilities;

import java.util.Optional;

public class RunNameContext {
    public static Optional<String> create(JsonNode root) {
        return ContextUtilities.Text.create(root);
    }
}
