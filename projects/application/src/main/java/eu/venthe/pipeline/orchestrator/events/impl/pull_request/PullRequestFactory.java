package eu.venthe.pipeline.orchestrator.events.impl.pull_request;

import eu.venthe.pipeline.orchestrator.events.TriggerEvent;
import eu.venthe.pipeline.orchestrator.events.contexts.ActionContext;
import eu.venthe.pipeline.orchestrator.events.impl.AbstractPullRequestEvent;
import eu.venthe.pipeline.orchestrator.events.model.EventType;
import org.springframework.stereotype.Service;

@SuppressWarnings("unchecked")
@Service
public class PullRequestFactory {
    public <T extends AbstractPullRequestEvent> T create(TriggerEvent event) {
        if (!event.getType().equals(EventType.PULL_REQUEST)) {
            throw new UnsupportedOperationException();
        }

        return switch (ActionContext.ensure(event.eject().get("action"))) {
            case READY_FOR_REVIEW -> (T) event.specify(PullRequestReadyForReviewEvent::new);
            case OPENED -> (T) event.specify(PullRequestOpenedEvent::new);
            case CLOSED -> (T) event.specify(PullRequestClosedEvent::new);
            default -> throw new UnsupportedOperationException();
        };
    }
}
