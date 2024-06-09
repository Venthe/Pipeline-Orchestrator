package eu.venthe.pipeline.orchestrator.config.infrastructure.message_broker;

import java.time.ZonedDateTime;

public interface Envelope <T> {
    ZonedDateTime getTime();
    T getData();
}
