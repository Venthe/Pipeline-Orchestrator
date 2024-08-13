package eu.venthe.platform.shared_kernel.events;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@RequiredArgsConstructor
@Getter
public class Envelope<T> {
    private final ZonedDateTime time = ZonedDateTime.now();
    @EqualsAndHashCode.Include
    private final T data;

    public static List<Envelope<?>> from(final List<DomainTrigger> left) {
        return left.stream().map(Envelope::new).collect(Collectors.toUnmodifiableList());
    }
}
