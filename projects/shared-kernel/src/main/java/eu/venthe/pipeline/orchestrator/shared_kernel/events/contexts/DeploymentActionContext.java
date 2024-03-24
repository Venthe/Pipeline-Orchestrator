package eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts;

import com.fasterxml.jackson.databind.JsonNode;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts.utilities.ContextUtilities;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.model.DeploymentAction;
import lombok.experimental.UtilityClass;

import static eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts.utilities.ContextUtilities.fromTextMapper;

@UtilityClass
public class DeploymentActionContext {
    public static DeploymentAction ensure(final JsonNode action) {
        return ContextUtilities.ensure(action, fromTextMapper(value -> DeploymentAction.of(value)
                .orElseThrow(IllegalArgumentException::new)));
    }
}
