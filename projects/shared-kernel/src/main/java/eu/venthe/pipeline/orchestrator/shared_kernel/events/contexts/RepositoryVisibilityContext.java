package eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts;

import com.fasterxml.jackson.databind.JsonNode;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts.utilities.ContextUtilities;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.model.RepositoryVisibility;
import lombok.experimental.UtilityClass;

@UtilityClass
public class RepositoryVisibilityContext {
    public static RepositoryVisibility ensure(final JsonNode visibility) {
        return ContextUtilities.ensureOptional(visibility, ContextUtilities.fromTextMapper(RepositoryVisibility::of));
    }
}
