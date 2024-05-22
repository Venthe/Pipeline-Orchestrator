package eu.venthe.pipeline.orchestrator.projects._projects.domain.workflows.contexts;

import com.fasterxml.jackson.databind.JsonNode;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.contexts.utilities.ContextUtilities;
import lombok.experimental.UtilityClass;

import java.util.Optional;

@UtilityClass
public class NameContext {
    public static Optional<String> create(JsonNode root) {
        return ContextUtilities.Text.create(root);
    }
}
