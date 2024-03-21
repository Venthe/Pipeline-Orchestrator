package eu.venthe.pipeline.orchestrator.projects.domain.workflows.contexts.on;

import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts.utilities.ContextUtilities;
import eu.venthe.pipeline.orchestrator.projects.domain.utilities.GlobPatternMatching;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class AbstractOnPropertyAndIgnoredProperty {
    protected final ObjectNode root;

    protected final List<String> patterns;
    protected final List<String> patternsIgnore;

    public AbstractOnPropertyAndIgnoredProperty(ObjectNode root, String patternsKey, String patternsIgnoreKey) {
        if (!root.isObject()) throw new IllegalArgumentException();

        this.root = root;
        this.patterns =  getList(patternsKey);
        this.patternsIgnore =  getList(patternsIgnoreKey);
    }

    private List<String> getList(String path) {
        return ContextUtilities.createStringCollection(this.root.get(path), Collectors.toList())
                .orElse(Collections.emptyList());
    }

    public boolean match(String property) {
        return match(List.of(property));
    }

    public boolean match(Collection<String> properties) {
        List<Boolean> votes = new ArrayList<>();

        votes.add(patterns.stream().anyMatch(pattern -> isMatching(properties, pattern)) || patterns.isEmpty());
        votes.add(patternsIgnore.stream().noneMatch(pattern -> isMatching(properties, pattern)) || patternsIgnore.isEmpty());

        boolean result = votes.stream().allMatch(Boolean.TRUE::equals);
        log.info("Matching - {}", result);
        return result;
    }

    private static boolean isMatching(Collection<String> properties, String pattern) {
        return properties.stream()
                .anyMatch(path -> GlobPatternMatching.isMatching(pattern, path));
    }
}
