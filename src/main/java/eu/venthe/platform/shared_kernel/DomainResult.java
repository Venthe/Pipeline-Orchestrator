package eu.venthe.platform.shared_kernel;

import eu.venthe.platform.shared_kernel.events.DomainMessage;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public record DomainResult<T>(T data, Collection<DomainMessage> messages) {

    public static <T> DomainResult<T> from(T data, Collection<DomainMessage> messages) {
        return new DomainResult<>(data, messages);
    }

    public static <T> DomainResult<T> from(T data, DomainMessage... messages) {
        return from(data, Arrays.stream(messages).toList());
    }

    public static <T> DomainResult<T> from(T data) {
        return from(data, Collections.emptyList());
    }
}
