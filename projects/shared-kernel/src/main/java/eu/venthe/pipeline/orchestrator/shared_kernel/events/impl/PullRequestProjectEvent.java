package eu.venthe.pipeline.orchestrator.shared_kernel.events.impl;

import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.AbstractProjectEvent;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.EventType;

public class PullRequestProjectEvent extends AbstractProjectEvent {
    protected PullRequestProjectEvent(ObjectNode root) {
        super(root, EventType.PULL_REQUEST);
    }
}
