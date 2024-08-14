package eu.venthe.platform.shared_kernel.system_events.contexts.git;

import com.fasterxml.jackson.databind.JsonNode;
import eu.venthe.platform.shared_kernel.git.SimpleRevision;
import eu.venthe.platform.shared_kernel.system_events.contexts.utilities.ContextUtilities;
import lombok.experimental.UtilityClass;

import java.util.Optional;

import static eu.venthe.platform.shared_kernel.system_events.contexts.utilities.ContextUtilities.fromTextMapper;

@UtilityClass
public class SimpleRevisionContext {
    public static SimpleRevision ensure(final JsonNode ref) {
        return ContextUtilities.ensure(ref, fromTextMapper(SimpleRevision::new));
    }

    public static Optional<SimpleRevision> create(final JsonNode ref) {
        return ContextUtilities.create(ref, fromTextMapper(SimpleRevision::new));
    }
}
