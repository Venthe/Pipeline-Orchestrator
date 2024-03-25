package eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts.model;

import com.fasterxml.jackson.databind.JsonNode;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts.utilities.ContextUtilities;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.model.EventType;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.model.UserType;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UserTypeContext {
    public static UserType ensure(final JsonNode root) {
        return ContextUtilities.Text.create(root)
                .flatMap(UserType::of)
                .orElseThrow(() -> new IllegalArgumentException("Event type must be present and matching"));
    }
}
