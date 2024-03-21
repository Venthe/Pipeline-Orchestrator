package eu.venthe.pipeline.orchestrator.shared_kernel.events._archive.contexts;

import com.fasterxml.jackson.databind.JsonNode;
import eu.venthe.pipeline.orchestrator.shared_kernel.events._archive.contexts.definitions.Commit;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts.utilities.ContextUtilities;
import lombok.experimental.UtilityClass;

import java.util.Set;
import java.util.stream.Collectors;

@UtilityClass
public class CommitsContext {
    public static Set<Commit> ensure(JsonNode commits) {
        return ContextUtilities.ensureObjectCollection(commits, CommitContext::ensure, Collectors.toSet());
    }
}
