package eu.venthe.pipeline.shared_kernel.system_events.contexts.model;

import com.fasterxml.jackson.databind.JsonNode;
import eu.venthe.pipeline.shared_kernel.system_events.contexts.utilities.ContextUtilities;
import eu.venthe.pipeline.shared_kernel.system_events.model.PullRequestAction;
import lombok.experimental.UtilityClass;

import static eu.venthe.pipeline.shared_kernel.system_events.contexts.utilities.ContextUtilities.fromTextMapper;

@UtilityClass
public class PullRequestActionContext {
    public static PullRequestAction ensure(final JsonNode action) {
        return ContextUtilities.ensureOptional(action, fromTextMapper(PullRequestAction::of));
    }
}
