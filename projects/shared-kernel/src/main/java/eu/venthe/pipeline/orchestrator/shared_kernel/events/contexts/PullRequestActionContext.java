package eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts;

import com.fasterxml.jackson.databind.JsonNode;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts.utilities.ContextUtilities;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.model.PullRequestAction;

import static eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts.utilities.ContextUtilities.fromTextMapper;

public class PullRequestActionContext {
    public static PullRequestAction ensure(JsonNode action) {
        return ContextUtilities.ensure(action, fromTextMapper(value -> PullRequestAction.of(value).orElseThrow()));
    }
}
