package eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts;

import com.fasterxml.jackson.databind.JsonNode;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts.utilities.ContextUtilities;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.model.RepositoryVisibility;

public class RepositoryVisibilityContext {
    public static RepositoryVisibility ensure(JsonNode visibility) {
        return ContextUtilities.ensure(visibility, ContextUtilities.fromTextMapper(RepositoryVisibility::of)).orElseThrow();
    }
}
