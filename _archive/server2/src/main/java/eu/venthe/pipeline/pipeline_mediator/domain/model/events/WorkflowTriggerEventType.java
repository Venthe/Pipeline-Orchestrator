package eu.venthe.pipeline.pipeline_mediator.domain.model.events;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum WorkflowTriggerEventType {
    BRANCH_PROTECTION_RULE("branch_protection_rule"),
    CHECK_RUN("check_run"),
    CHECK_SUITE("check_suite"),
    CREATE("create"),
    DELETE("delete"),
    DEPLOYMENT("deployment"),
    DEPLOYMENT_STATUS("deployment_status"),
    DISCUSSION("discussion"),
    DISCUSSION_COMMENT("discussion_comment"),
    FORK("fork"),
    GOLLUM("gollum"),
    ISSUE_COMMENT("issue_comment"),
    ISSUES("issues"),
    LABEL("label"),
    MERGE_GROUP("merge_group"),
    MILESTONE("milestone"),
    PAGE_BUILD("page_build"),
    PROJECT("project"),
    PROJECT_CARD("project_card"),
    PROJECT_COLUMN("project_column"),
    PUBLIC("public"),
    PULL_REQUEST("pull_request"),
    PULL_REQUEST_REVIEW("pull_request_review"),
    PULL_REQUEST_REVIEW_COMMENT("pull_request_review_comment"),
    PULL_REQUEST_TARGET("pull_request_target"),
    PUSH("push"),
    REGISTRY_PACKAGE("registry_package"),
    RELEASE("release"),
    REPOSITORY_DISPATCH("repository_dispatch"),
    SCHEDULE("schedule"),
    STATUS("status"),
    WATCH("watch"),
    WORKFLOW_CALL("workflow_call"),
    WORKFLOW_DISPATCH("workflow_dispatch"),
    WORKFLOW_RUN("workflow_run");

    @Getter
    private final String value;
}
