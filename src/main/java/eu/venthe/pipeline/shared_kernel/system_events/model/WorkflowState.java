package eu.venthe.pipeline.shared_kernel.system_events.model;

import com.google.common.collect.MoreCollectors;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import static java.util.Arrays.stream;

@Getter
@RequiredArgsConstructor
public enum WorkflowState {
    ACTIVE("active"),
    DELETED("deleted"),
    DISABLED_INACTIVITY("disabled_inactivity"),
    DISABLED_MANUALLY("disabled_manually");

    private final String value;

    public static Optional<WorkflowState> of(String type) {
        if (type == null) {
            return Optional.empty();
        }

        return stream(values())
                .filter(eventType -> eventType.getValue().equalsIgnoreCase(type.trim()))
                .collect(MoreCollectors.toOptional());
    }
}
