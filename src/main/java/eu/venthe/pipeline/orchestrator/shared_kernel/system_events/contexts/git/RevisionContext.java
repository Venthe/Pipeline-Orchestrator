package eu.venthe.pipeline.orchestrator.shared_kernel.system_events.contexts.git;

import com.fasterxml.jackson.databind.JsonNode;
import eu.venthe.pipeline.orchestrator.shared_kernel.git.GitRevision;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.contexts.utilities.ContextUtilities;
import lombok.experimental.UtilityClass;

import java.util.Optional;

import static eu.venthe.pipeline.orchestrator.shared_kernel.system_events.contexts.utilities.ContextUtilities.fromTextMapper;

@UtilityClass
public class RevisionContext {
    public static GitRevision ensure(final JsonNode ref) {
        return ContextUtilities.ensure(ref, fromTextMapper(GitRevision::new));
    }

    public static Optional<GitRevision> create(final JsonNode ref) {
        return ContextUtilities.create(ref, fromTextMapper(GitRevision::new));
    }
}
