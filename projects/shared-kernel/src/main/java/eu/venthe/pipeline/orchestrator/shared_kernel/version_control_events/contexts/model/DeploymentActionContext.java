package eu.venthe.pipeline.orchestrator.shared_kernel.version_control_events.contexts.model;

import com.fasterxml.jackson.databind.JsonNode;
import eu.venthe.pipeline.orchestrator.shared_kernel.version_control_events.contexts.utilities.ContextUtilities;
import eu.venthe.pipeline.orchestrator.shared_kernel.version_control_events.model.DeploymentAction;
import lombok.experimental.UtilityClass;

import static eu.venthe.pipeline.orchestrator.shared_kernel.version_control_events.contexts.utilities.ContextUtilities.fromTextMapper;

@UtilityClass
public class DeploymentActionContext {
    public static DeploymentAction ensure(final JsonNode action) {
        return ContextUtilities.ensure(action, fromTextMapper(value -> DeploymentAction.of(value)
                .orElseThrow(IllegalArgumentException::new)));
    }
}