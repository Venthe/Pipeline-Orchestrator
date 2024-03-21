package eu.venthe.pipeline.orchestrator.shared_kernel.events.impl;

import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.AbstractProjectEvent;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.EventType;

public class DeploymentStatusProjectEvent extends AbstractProjectEvent {
    protected DeploymentStatusProjectEvent(ObjectNode root) {
        super(root, EventType.DEPLOYMENT_STATUS);
    }
}
