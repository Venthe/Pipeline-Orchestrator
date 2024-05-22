package eu.venthe.pipeline.orchestrator.projects._projects.domain.workflows.contexts.on.matchers;

import com.fasterxml.jackson.databind.JsonNode;

public class OnTags extends AbstractOnPropertyAndIgnoredProperty {
    public OnTags(JsonNode root) {
        super(root, "tags", "tagsIgnore");
    }

    public static OnTags create(JsonNode root) {
        return new OnTags(root);
    }
}
