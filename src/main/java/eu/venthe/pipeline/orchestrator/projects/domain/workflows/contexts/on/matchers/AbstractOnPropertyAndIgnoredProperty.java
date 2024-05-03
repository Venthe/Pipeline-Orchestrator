package eu.venthe.pipeline.orchestrator.projects.domain.workflows.contexts.on.matchers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.pipeline.orchestrator.projects.domain.utilities.GlobPatternMatching;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.contexts.utilities.ContextUtilities;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

@Slf4j
public class AbstractOnPropertyAndIgnoredProperty {
    protected final ObjectNode root;

    protected final List<String> patterns;
    protected final List<String> patternsIgnore;

    private final String name;

    public AbstractOnPropertyAndIgnoredProperty(JsonNode _root, String patternsKey, String patternsIgnoreKey) {
        if (!_root.isObject()) throw new IllegalArgumentException();

        this.root = (ObjectNode) _root;
        this.patterns = getList(patternsKey);
        this.patternsIgnore = getList(patternsIgnoreKey);

        this.name = patternsKey;
    }

    private List<String> getList(String path) {
        JsonNode node = this.root.get(path);
        return ContextUtilities.Collection.createCollection(node,
                stream -> stream.filter(Predicate.not(JsonNode::isNull))
                        .filter(JsonNode::isTextual)
                        .map(JsonNode::asText)
                        .filter(Predicate.not(String::isBlank))
                        .toList());
    }

    public boolean match(String property) {
        return match(List.of(property));
    }

    public boolean match(Collection<String> properties) {
        List<Boolean> votes = new ArrayList<>();

        votes.add(patterns.stream().anyMatch(pattern -> isMatching(properties, pattern)) || patterns.isEmpty());
        votes.add(patternsIgnore.stream().noneMatch(pattern -> isMatching(properties, pattern)) || patternsIgnore.isEmpty());

        boolean result = votes.stream().allMatch(Boolean.TRUE::equals);
        log.info("Matching '{}': {}", name, result);
        return result;
    }

    private static boolean isMatching(Collection<String> properties, String pattern) {
        return properties.stream()
                .anyMatch(path -> GlobPatternMatching.isMatching(pattern, path));
    }
}