package eu.venthe.pipeline.orchestrator.shared_kernel.version_control_events.contexts.git;

import com.fasterxml.jackson.databind.JsonNode;
import eu.venthe.pipeline.orchestrator.shared_kernel.version_control_events.contexts.utilities.ContextUtilities;
import lombok.experimental.UtilityClass;

import java.util.Optional;

import static eu.venthe.pipeline.orchestrator.shared_kernel.version_control_events.contexts.utilities.ContextUtilities.toTextMapper;

@UtilityClass
public class ReferenceContext {
    public static String ensure(final JsonNode ref) {
        return ContextUtilities.ensure(ref, toTextMapper());
    }

    public static Optional<String> create(final JsonNode ref) {
        return ContextUtilities.create(ref, toTextMapper());
    }
}
