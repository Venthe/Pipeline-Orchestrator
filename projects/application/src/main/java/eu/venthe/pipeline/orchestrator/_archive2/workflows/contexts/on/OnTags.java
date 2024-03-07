package eu.venthe.pipeline.orchestrator._archive2.workflows.contexts.on;

import com.fasterxml.jackson.databind.node.ObjectNode;

public class OnTags extends AbstractOnPropertyAndIgnoredProperty {
    public OnTags(ObjectNode root) {
        super(root, "tags", "tagsIgnore");
    }

    public static OnTags create(ObjectNode root) {
        return new OnTags(root);
    }
}
