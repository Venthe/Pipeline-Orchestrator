package eu.venthe.pipeline.orchestrator.projects.domain.workflows.contexts.on.matchers.on;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class OnTags extends AbstractOnPropertyAndIgnoredProperty {
    public OnTags(JsonNode root) {
        super(root, "tags", "tagsIgnore");
    }

    public static OnTags create(JsonNode root) {
        return new OnTags(root);
    }
}
