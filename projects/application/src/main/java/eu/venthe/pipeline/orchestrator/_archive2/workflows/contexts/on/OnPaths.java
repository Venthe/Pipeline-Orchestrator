package eu.venthe.pipeline.orchestrator._archive2.workflows.contexts.on;

import com.fasterxml.jackson.databind.node.ObjectNode;

public class OnPaths extends AbstractOnPropertyAndIgnoredProperty {
    public OnPaths(ObjectNode root) {
        super(root, "paths", "pathsIgnore");
    }

    public static OnPaths create(ObjectNode root) {
        return new OnPaths(root);
    }
}
