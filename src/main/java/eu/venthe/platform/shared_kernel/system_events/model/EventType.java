package eu.venthe.platform.shared_kernel.system_events.model;

import com.google.common.collect.MoreCollectors;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import static java.util.Arrays.stream;

@Getter
@RequiredArgsConstructor
public enum EventType {
    CREATE("create"),
    DELETE("delete"),
    DEPLOYMENT("deployment"),
    DEPLOYMENT_REVIEW("deployment_review"),
    DEPLOYMENT_STATUS("deployment_status"),
    PULL_REQUEST("pull_request"),
    PULL_REQUEST_REVIEW_COMMENT("pull_request_review_comment"),
    PULL_REQUEST_REVIEW("pull_request_review"),
    PUSH("push"),
    WORKFLOW_DISPATCH("workflow_dispatch"),
    WORKFLOW_JOB("workflow_job"),
    WORKFLOW_RUN("workflow_run");

    private final String value;

    public static Optional<EventType> of(String type) {
        if (type == null) {
            return Optional.empty();
        }

        return stream(values())
                .filter(eventType -> eventType.getValue().equalsIgnoreCase(type.trim()))
                .collect(MoreCollectors.toOptional());
    }
}
