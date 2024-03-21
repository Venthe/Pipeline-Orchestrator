package eu.venthe.pipeline.orchestrator.shared_kernel.events.impl;

import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.AbstractProjectEvent;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.EventType;

public class ProjectProjectEvent extends AbstractProjectEvent {
    protected ProjectProjectEvent(ObjectNode root) {
        super(root, EventType.PROJECT);
    }
}
