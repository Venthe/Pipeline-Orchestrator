package eu.venthe.platform.shared_kernel.system_events.model;

import com.google.common.collect.MoreCollectors;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import static java.util.Arrays.stream;

@Getter
@RequiredArgsConstructor
public enum DeploymentStatusAction {
    CREATED("created");

    private final String value;

    public static Optional<DeploymentStatusAction> of(String type) {
        if (type == null) {
            return Optional.empty();
        }

        return stream(values())
                .filter(eventType -> eventType.getValue().equalsIgnoreCase(type.trim()))
                .collect(MoreCollectors.toOptional());
    }
}
