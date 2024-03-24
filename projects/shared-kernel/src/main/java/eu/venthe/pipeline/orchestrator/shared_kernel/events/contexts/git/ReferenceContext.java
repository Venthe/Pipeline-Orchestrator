package eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts.git;

import com.fasterxml.jackson.databind.JsonNode;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts.utilities.ContextUtilities;
import lombok.experimental.UtilityClass;

import static eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts.utilities.ContextUtilities.toTextMapper;

@UtilityClass
public class ReferenceContext {
    public static String ensure(JsonNode masterBranch) {
        return ContextUtilities.ensure(masterBranch, toTextMapper());
    }
}
