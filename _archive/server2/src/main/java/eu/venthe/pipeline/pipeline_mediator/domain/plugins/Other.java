package eu.venthe.pipeline.pipeline_mediator.domain.plugins;

import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.pipeline.pipeline_mediator.infrastructure.queue.kafka.QueuedEvent;

import java.util.Optional;

public interface Other {

    Optional<QueuedEvent> handleGerritEvent(ObjectNode event);
}
