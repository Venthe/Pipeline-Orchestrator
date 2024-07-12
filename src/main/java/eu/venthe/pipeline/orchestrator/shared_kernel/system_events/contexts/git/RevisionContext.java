package eu.venthe.pipeline.orchestrator.shared_kernel.system_events.contexts.git;

import com.fasterxml.jackson.databind.JsonNode;
import eu.venthe.pipeline.orchestrator.shared_kernel.git.Revision;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.contexts.utilities.ContextUtilities;
import lombok.experimental.UtilityClass;

import java.util.Optional;

import static eu.venthe.pipeline.orchestrator.shared_kernel.system_events.contexts.utilities.ContextUtilities.fromTextMapper;

@UtilityClass
public class RevisionContext {
    public static Revision ensure(final JsonNode ref) {
        return ContextUtilities.ensure(ref, fromTextMapper(Revision::new));
    }

    public static Optional<Revision> create(final JsonNode ref) {
        return ContextUtilities.create(ref, fromTextMapper(Revision::new));
    }
}
