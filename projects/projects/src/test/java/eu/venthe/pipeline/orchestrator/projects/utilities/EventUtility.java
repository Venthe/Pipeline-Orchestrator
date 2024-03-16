package eu.venthe.pipeline.orchestrator.projects.utilities;

import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.pipeline.orchestrator.projects.domain.events.HandledEvent;
import eu.venthe.pipeline.orchestrator.projects.domain.events.TriggerEvent;
import eu.venthe.pipeline.orchestrator.projects.domain.events.impl.WorkflowDispatchEvent;
import eu.venthe.pipeline.orchestrator.projects.domain.events.impl.PushEvent;
import eu.venthe.pipeline.orchestrator.projects.domain.events.impl.pull_request.PullRequestFactory;
import lombok.experimental.UtilityClass;

@UtilityClass
public class EventUtility {
    public static HandledEvent eventMapper(TriggerEvent event) {
        return switch (event.getType()) {
            case WORKFLOW_DISPATCH -> event.specify(WorkflowDispatchEvent::new);
            case PUSH -> event.specify(PushEvent::new);
            case PULL_REQUEST -> new PullRequestFactory().create(event);
            default -> throw new UnsupportedOperationException("Unhandled event: " + event.getType().getValue());
        };
    }

    public static HandledEvent eventMapper(ObjectNode root) {
        return eventMapper(new TriggerEvent(root));
    }
}
