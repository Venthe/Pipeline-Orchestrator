package eu.venthe.pipeline.orchestrator.shared_kernel.events;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.ZonedDateTime;

@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@RequiredArgsConstructor
@Getter
public class Envelope<T> {
    private final ZonedDateTime time = ZonedDateTime.now();
    @EqualsAndHashCode.Include
    private final T data;
}
