package eu.venthe.pipeline.orchestrator.shared_kernel.events;

import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.AbstractProjectEvent;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.model.EventType;
import lombok.Getter;
import lombok.ToString;

import java.time.ZonedDateTime;

@Getter
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class PullRequestProjectEvent extends AbstractProjectEvent {
    protected PullRequestProjectEvent(ObjectNode root, ZonedDateTime timestamp) {
        super(root, EventType.PULL_REQUEST, timestamp);
    }
}
