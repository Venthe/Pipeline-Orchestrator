package eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts;

import com.fasterxml.jackson.databind.JsonNode;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts.utilities.ContextUtilities;
import lombok.experimental.UtilityClass;

import java.util.Optional;

/**
 * description: The repository's description.
 */
@UtilityClass
public class ProjectDescriptionContext {
    public static Optional<String> create(final JsonNode description) {
        return ContextUtilities.Text.create(description);
    }
}
