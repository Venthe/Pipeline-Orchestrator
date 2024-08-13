package eu.venthe.platform.shared_kernel.system_events.contexts.model;

import com.fasterxml.jackson.databind.JsonNode;
import eu.venthe.platform.shared_kernel.system_events.contexts.utilities.ContextUtilities;
import eu.venthe.platform.shared_kernel.system_events.model.RepositoryVisibility;
import lombok.experimental.UtilityClass;

@UtilityClass
public class RepositoryVisibilityContext {
    public static RepositoryVisibility ensure(final JsonNode visibility) {
        return ContextUtilities.ensureOptional(visibility, ContextUtilities.fromTextMapper(RepositoryVisibility::of));
    }
}