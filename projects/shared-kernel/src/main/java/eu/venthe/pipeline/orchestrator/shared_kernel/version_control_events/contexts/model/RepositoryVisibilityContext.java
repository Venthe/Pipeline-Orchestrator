package eu.venthe.pipeline.orchestrator.shared_kernel.version_control_events.contexts.model;

import com.fasterxml.jackson.databind.JsonNode;
import eu.venthe.pipeline.orchestrator.shared_kernel.version_control_events.contexts.utilities.ContextUtilities;
import eu.venthe.pipeline.orchestrator.shared_kernel.version_control_events.model.RepositoryVisibility;
import lombok.experimental.UtilityClass;

@UtilityClass
public class RepositoryVisibilityContext {
    public static RepositoryVisibility ensure(final JsonNode visibility) {
        return ContextUtilities.ensureOptional(visibility, ContextUtilities.fromTextMapper(RepositoryVisibility::of));
    }
}
