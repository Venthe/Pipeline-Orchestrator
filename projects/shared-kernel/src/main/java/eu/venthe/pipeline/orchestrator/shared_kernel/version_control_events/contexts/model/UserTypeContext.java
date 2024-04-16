package eu.venthe.pipeline.orchestrator.shared_kernel.version_control_events.contexts.model;

import com.fasterxml.jackson.databind.JsonNode;
import eu.venthe.pipeline.orchestrator.shared_kernel.version_control_events.contexts.utilities.ContextUtilities;
import eu.venthe.pipeline.orchestrator.shared_kernel.version_control_events.model.UserType;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UserTypeContext {
    public static UserType ensure(final JsonNode root) {
        return ContextUtilities.Text.create(root)
                .flatMap(UserType::of)
                .orElseThrow(() -> new IllegalArgumentException("Event type must be present and matching"));
    }
}
