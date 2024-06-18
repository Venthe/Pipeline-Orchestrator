package eu.venthe.pipeline.orchestrator.modules.workflow.domain.workflows.contexts.on.matchers;

import com.fasterxml.jackson.databind.JsonNode;

public class OnBranches extends AbstractOnPropertyAndIgnoredProperty {
    public OnBranches(JsonNode root) {
        super(root, "branches", "branchesIgnore");
    }

    public static OnBranches create(JsonNode root) {
        return new OnBranches(root);
    }
}
