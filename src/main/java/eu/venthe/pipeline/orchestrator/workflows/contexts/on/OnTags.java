package eu.venthe.pipeline.orchestrator.workflows.contexts.on;

import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.List;

public class OnTags extends AbstractOnPropertyAndIgnoredProperty {
    public OnTags(ObjectNode root) {
        super(root, "tags", "tagsIgnore");
    }

    public static OnTags create(ObjectNode root) {
        return new OnTags(root);
    }
}
