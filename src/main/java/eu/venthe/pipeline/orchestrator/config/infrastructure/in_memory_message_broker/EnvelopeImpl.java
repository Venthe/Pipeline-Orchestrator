package eu.venthe.pipeline.orchestrator.config.infrastructure.in_memory_message_broker;

import eu.venthe.pipeline.orchestrator.config.infrastructure.message_broker.Envelope;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.ZonedDateTime;

@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@RequiredArgsConstructor
@Getter
public class EnvelopeImpl<T> implements Envelope<T> {
    private final ZonedDateTime time = ZonedDateTime.now();
    @EqualsAndHashCode.Include
    private final T data;
}
