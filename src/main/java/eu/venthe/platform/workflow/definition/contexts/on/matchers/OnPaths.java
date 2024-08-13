package eu.venthe.platform.workflow.definition.contexts.on.matchers;

import com.fasterxml.jackson.databind.JsonNode;

public class OnPaths extends AbstractOnPropertyAndIgnoredProperty {
    public OnPaths(JsonNode root) {
        super(root, "paths", "pathsIgnore");
    }

    public static OnPaths create(JsonNode root) {
        return new OnPaths(root);
    }
}
