package eu.venthe.pipeline.orchestrator.shared_kernel.events;

import com.google.common.collect.MoreCollectors;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import static java.util.Arrays.stream;

@Getter
@RequiredArgsConstructor
public enum EventType {
    CREATE("Create"),
    DELETE("Delete"),
    DEPLOYMENT("Deployment"),
    DEPLOYMENT_REVIEW("DeploymentReview"),
    DEPLOYMENT_STATUS("DeploymentStatus"),
    PROJECT("Project"),
    PULL_REQUEST("PullRequest"),
    PULL_REQUEST_REVIEW_COMMENT("PullRequestReviewComment"),
    PULL_REQUEST_REVIEW("PullRequestReview"),
    PUSH("Push"),
    WORKFLOW_DISPATCH("WorkflowDispatch"),
    WORKFLOW_JOB("WorkflowJob"),
    WORKFLOW_RUN("WorkflowRun");

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
