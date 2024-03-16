package eu.venthe.pipeline.orchestrator.projects.domain.events.model;

import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.UUID;

public interface Event {
    EventType getType();

    UUID getId();

    ObjectNode eject();
}
