package eu.venthe.pipeline.orchestrator._archive2.events.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import static com.google.common.collect.MoreCollectors.toOptional;
import static java.util.Arrays.stream;

@Getter
@RequiredArgsConstructor
public enum EventType {
    // BRANCH_PROTECTION_RULE("branch_protection_rule"),
    // CHECK_RUN("check_run"),
    // CHECK_SUITE("check_suite"),
    // DEPLOYMENT("deployment"),
    // DEPLOYMENT_STATUS("deployment_status"),
    // DISCUSSION("discussion"),
    // DISCUSSION_COMMENT("discussion_comment"),
    // FORK("fork"),
    // GOLLUM("gollum"),
    // MERGE_GROUP("merge_group"),
    // MILESTONE("milestone"),
    // PAGE_BUILD("page_build"),
    // PROJECT("project"),
    // PROJECT_CARD("project_card"),
    // PROJECT_COLUMN("project_column"),
    // PUBLIC("public"),
    // REGISTRY_PACKAGE("registry_package"),
    // RELEASE("release"),
    // REPOSITORY_DISPATCH("repository_dispatch"),
    // STATUS("status"),
    // WATCH("watch"),
    // @Deprecated
    // PULL_REQUEST_COMMENT("pull_request_comment"),
    CREATE("create"),
    DELETE("delete"),
    ISSUE_COMMENT("issue_comment"),
    ISSUES("issues"),
    LABEL("label"),
    PULL_REQUEST("pull_request"),
    PULL_REQUEST_REVIEW("pull_request_review"),
    PULL_REQUEST_REVIEW_COMMENT("pull_request_review_comment"),
    PULL_REQUEST_TARGET("pull_request_target"),
    PUSH("push"),
    SCHEDULE("schedule"),
    WORKFLOW_CALL("workflow_call"),
    WORKFLOW_DISPATCH("workflow_dispatch"),
    WORKFLOW_RUN("workflow_run");

    private final String value;

    public static Optional<EventType> of(String type) {
        if (type == null) {
            return Optional.empty();
        }

        return stream(values())
                .filter(eventType -> eventType.getValue().equalsIgnoreCase(type.trim()))
                .collect(toOptional());
    }
}
