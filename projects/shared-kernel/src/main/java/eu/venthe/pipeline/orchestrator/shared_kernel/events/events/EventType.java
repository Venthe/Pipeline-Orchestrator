package eu.venthe.pipeline.orchestrator.shared_kernel.events.events;

import com.google.common.collect.MoreCollectors;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import static java.util.Arrays.stream;

@Getter
@RequiredArgsConstructor
public enum EventType {
    CREATE_EVENT("CreateEvent"),
    DELETE_EVENT("DeleteEvent"),
    DEPLOYMENT_EVENT("DeploymentEvent"),
    DEPLOYMENT_REVIEW_EVENT("DeploymentReviewEvent"),
    DEPLOYMENT_STATUS_EVENT("DeploymentStatusEvent"),
    PROJECT_EVENT("ProjectEvent"),
    PULL_REQUEST_EVENT("PullRequestEvent"),
    PULL_REQUEST_REVIEW_COMMENT_EVENT("PullRequestReviewCommentEvent"),
    PULL_REQUEST_REVIEW_EVENT("PullRequestReviewEvent"),
    PUSH_EVENT("PushEvent"),
    WORKFLOW_DISPATCH_EVENT("WorkflowDispatchEvent"),
    WORKFLOW_JOB_EVENT("WorkflowJobEvent"),
    WORKFLOW_RUN_EVENT("WorkflowRunEvent");

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
