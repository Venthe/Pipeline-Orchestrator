package eu.venthe.pipeline.orchestrator.projects.domain.workflows.contexts.on;

import com.fasterxml.jackson.databind.node.ObjectNode;

public class OnBranches extends AbstractOnPropertyAndIgnoredProperty {
    public OnBranches(ObjectNode root) {
        super(root, "branches", "branchesIgnore");
    }

    public static OnBranches create(ObjectNode root) {
        return new OnBranches(root);
    }
}
