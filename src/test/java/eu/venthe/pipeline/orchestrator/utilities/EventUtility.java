package eu.venthe.pipeline.orchestrator.utilities;

import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.pipeline.orchestrator.events.HandledEvent;
import eu.venthe.pipeline.orchestrator.events.TriggerEvent;
import eu.venthe.pipeline.orchestrator.events.impl.PushEvent;
import eu.venthe.pipeline.orchestrator.events.impl.WorkflowDispatchEvent;
import eu.venthe.pipeline.orchestrator.events.impl.pull_request.PullRequestFactory;
import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;

@UtilityClass
public class EventUtility {
    @NotNull
    public static HandledEvent eventMapper(TriggerEvent event) {
        return switch (event.getType()) {
            case WORKFLOW_DISPATCH -> event.specify(WorkflowDispatchEvent::new);
            case PUSH -> event.specify(PushEvent::new);
            case PULL_REQUEST -> new PullRequestFactory().create(event);
            default -> throw new UnsupportedOperationException("Unhandled event: " + event.getType().getValue());
        };
    }

    @NotNull
    public static HandledEvent eventMapper(ObjectNode root) {
        return eventMapper(new TriggerEvent(root));
    }
}
