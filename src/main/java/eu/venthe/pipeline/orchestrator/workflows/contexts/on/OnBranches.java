package eu.venthe.pipeline.orchestrator.workflows.contexts.on;

import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.List;

public class OnBranches extends AbstractOnPropertyAndIgnoredProperty {
    public OnBranches(ObjectNode root) {
        super(root, "branches", "branchesIgnore");
    }

    public static OnBranches create(ObjectNode root) {
        return new OnBranches(root);
    }
}
