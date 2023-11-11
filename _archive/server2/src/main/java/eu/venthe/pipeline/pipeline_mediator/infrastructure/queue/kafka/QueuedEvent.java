package eu.venthe.pipeline.pipeline_mediator.infrastructure.queue.kafka;

import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Value
@EqualsAndHashCode
@Builder(access = AccessLevel.PROTECTED)
@Jacksonized
@AllArgsConstructor
public class QueuedEvent {
    @NonNull
    String referenceId;
}
