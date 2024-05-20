package eu.venthe.pipeline.orchestrator.shared_kernel.system_events.model;

import com.google.common.collect.MoreCollectors;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import static java.util.Arrays.stream;

@Getter
@RequiredArgsConstructor
public enum PullRequestReviewAction {
    DISMISSED("dismissed"),
    EDITED("edited"),
    SUBMITTED("submitted");

    private final String value;

    public static Optional<PullRequestReviewAction> of(String type) {
        if (type == null) {
            return Optional.empty();
        }

        return stream(values())
                .filter(eventType -> eventType.getValue().equalsIgnoreCase(type.trim()))
                .collect(MoreCollectors.toOptional());
    }
}
