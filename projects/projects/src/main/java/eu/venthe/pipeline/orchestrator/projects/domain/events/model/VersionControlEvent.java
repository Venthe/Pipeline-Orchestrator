package eu.venthe.pipeline.orchestrator.projects.domain.events.model;

import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.UUID;

public interface VersionControlEvent {
    EventType getType();

    UUID getId();

    ObjectNode eject();
}
