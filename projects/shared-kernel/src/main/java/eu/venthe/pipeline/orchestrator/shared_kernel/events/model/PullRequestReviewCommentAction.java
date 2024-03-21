package eu.venthe.pipeline.orchestrator.shared_kernel.events.model;

import com.google.common.collect.MoreCollectors;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import static java.util.Arrays.stream;

@Getter
@RequiredArgsConstructor
public enum PullRequestReviewCommentAction {
    CREATED("created"),
    DELETED("deleted"),
    EDITED("edited");

    private final String value;

    public static Optional<PullRequestReviewCommentAction> of(String type) {
        if (type == null) {
            return Optional.empty();
        }

        return stream(values())
                .filter(eventType -> eventType.getValue().equalsIgnoreCase(type.trim()))
                .collect(MoreCollectors.toOptional());
    }
}
