package eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts;

import com.fasterxml.jackson.databind.JsonNode;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.model.DeploymentAction;

import static eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts.ContextUtilities.fromText;

public class DeploymentActionContext {
    public static DeploymentAction ensure(JsonNode action) {
        return ContextUtilities.ensure(action, fromText(value -> DeploymentAction.of(value).orElseThrow()));
    }
}
