package eu.venthe.platform.shared_kernel.system_events.contexts.git;

import com.fasterxml.jackson.databind.JsonNode;
import eu.venthe.platform.shared_kernel.git.RevisionShortName;
import eu.venthe.platform.shared_kernel.system_events.contexts.utilities.ContextUtilities;
import lombok.experimental.UtilityClass;

import java.util.Optional;

import static eu.venthe.platform.shared_kernel.system_events.contexts.utilities.ContextUtilities.fromTextMapper;

@UtilityClass
public class RevisionShortNameContext {
    public static RevisionShortName ensure(final JsonNode ref) {
        return ContextUtilities.ensure(ref, fromTextMapper(RevisionShortName::new));
    }

    public static Optional<RevisionShortName> create(final JsonNode ref) {
        return ContextUtilities.create(ref, fromTextMapper(RevisionShortName::new));
    }
}
