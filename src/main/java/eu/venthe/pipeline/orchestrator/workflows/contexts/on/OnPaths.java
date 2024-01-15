package eu.venthe.pipeline.orchestrator.workflows.contexts.on;

import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.List;

public class OnPaths extends AbstractOnPropertyAndIgnoredProperty {
    public OnPaths(ObjectNode root) {
        super(root, "paths", "pathsIgnore");
    }

    public static OnPaths create(ObjectNode root) {
        return new OnPaths(root);
    }
}
