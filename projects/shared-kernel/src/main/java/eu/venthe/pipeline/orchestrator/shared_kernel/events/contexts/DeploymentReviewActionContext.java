package eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts;

import com.fasterxml.jackson.databind.JsonNode;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts.utilities.ContextUtilities;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.model.DeploymentReviewAction;

import static eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts.utilities.ContextUtilities.fromText;

public class DeploymentReviewActionContext {
    public static DeploymentReviewAction ensure(JsonNode action) {
        return ContextUtilities.ensure(action, fromText(value -> DeploymentReviewAction.of(value).orElseThrow()));
    }
}
