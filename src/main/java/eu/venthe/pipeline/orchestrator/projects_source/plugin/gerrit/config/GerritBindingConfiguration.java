package eu.venthe.pipeline.orchestrator.projects_source.plugin.gerrit.config;

import lombok.Getter;

import java.util.*;
import java.util.stream.Collectors;

@Getter
public class GerritBindingConfiguration {
    String url;
    String username;
    String password;
    Set<String> ignoredEvents;

    public GerritBindingConfiguration(Map<String, ?> root) {
        if (root == null) {
            throw new IllegalArgumentException();
        }

        url = Optional.ofNullable((String) root.get("url")).orElseThrow();
        username = Optional.ofNullable((String) root.get("username")).orElseThrow();
        password = Optional.ofNullable((String) root.get("password")).orElseThrow();
        ignoredEvents = Optional.ofNullable(root.get("ignoredEvents"))
                .map(a -> (Collection<String>) a)
                .map(arr -> arr.stream()
                        .map(node -> Optional.ofNullable((String) node).orElseThrow())
                        .collect(Collectors.toSet())
                )
                .orElse(Collections.emptySet());
    }

}
