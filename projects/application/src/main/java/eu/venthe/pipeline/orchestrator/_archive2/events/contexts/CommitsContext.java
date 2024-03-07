package eu.venthe.pipeline.orchestrator._archive2.events.contexts;

import com.fasterxml.jackson.databind.JsonNode;
import eu.venthe.pipeline.orchestrator._archive2.events.contexts.definitions.Commit;
import eu.venthe.pipeline.orchestrator._archive2.utilities.ContextUtilities;
import lombok.experimental.UtilityClass;

import java.util.Set;
import java.util.stream.Collectors;

@UtilityClass
public class CommitsContext {
    public static Set<Commit> ensure(JsonNode commits) {
        return ContextUtilities.ensureObjectCollection(commits, CommitContext::ensure, Collectors.toSet());
    }
}
