package eu.venthe.pipeline.orchestrator.projects.domain.common_contexts;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.TextNode;
import lombok.Getter;

import java.time.OffsetDateTime;

import static java.time.format.DateTimeFormatter.ISO_OFFSET_DATE_TIME;

public class JsonDateTime {
    private final TextNode property;

    @Getter
    private final OffsetDateTime date;

    public JsonDateTime(JsonNode root) {
        if (root == null) throw new IllegalArgumentException();
        if (!root.isTextual()) throw new IllegalArgumentException();

        this.property = (TextNode) root;
        date = OffsetDateTime.parse(this.property.asText(), ISO_OFFSET_DATE_TIME);
    }
}
