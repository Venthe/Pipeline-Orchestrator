package eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts.model;

import com.fasterxml.jackson.databind.JsonNode;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts.utilities.ContextUtilities;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.model.DeploymentReviewAction;
import lombok.experimental.UtilityClass;

import static eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts.utilities.ContextUtilities.fromTextMapper;

@UtilityClass
public class DeploymentReviewActionContext {
    public static DeploymentReviewAction ensure(final JsonNode action) {
        return ContextUtilities.ensure(action, fromTextMapper(value -> DeploymentReviewAction.of(value)
                .orElseThrow(IllegalArgumentException::new)));
    }
}
