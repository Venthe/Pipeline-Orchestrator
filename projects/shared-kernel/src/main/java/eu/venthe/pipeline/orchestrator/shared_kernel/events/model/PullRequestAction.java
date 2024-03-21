package eu.venthe.pipeline.orchestrator.shared_kernel.events.model;

import com.google.common.collect.MoreCollectors;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import static java.util.Arrays.stream;

@Getter
@RequiredArgsConstructor
public enum PullRequestAction {
    ASSIGNED("assigned"),
    AUTO_MERGE_DISABLED("auto_merge_disabled"),

    AUTO_MERGE_ENABLED("auto_merge_enabled"),

    CLOSED("closed"),

    CONVERTED_TO_DRAFT("converted_to_draft"),

    DEMILESTONED("demilestoned"),

    DEQUEUED("dequeued"),

    EDITED("edited"),

    ENQUEUED("enqueued"),

    LABELED("labeled"),

    LOCKED("locked"),

    MILESTONED("milestoned"),

    OPENED("opened"),

    READY_FOR_REVIEW("ready_for_review"),

    REOPENED("reopened"),

    REVIEW_REQUEST_REMOVED("review_request_removed"),

    REVIEW_REQUESTED("review_requested"),

    SYNCHRONIZE("synchronize"),

    UNASSIGNED("unassigned"),

    UNLABELED("unlabeled"),

    UNLOCKED("unlocked");

    private final String value;

    public static Optional<PullRequestAction> of(String type) {
        if (type == null) {
            return Optional.empty();
        }

        return stream(values())
                .filter(eventType -> eventType.getValue().equalsIgnoreCase(type.trim()))
                .collect(MoreCollectors.toOptional());
    }
}
