package eu.venthe.pipeline.orchestrator.plugins.gerrit;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.pipeline.orchestrator.plugins.ProjectProvider;
import eu.venthe.pipeline.orchestrator.plugins.gerrit.config.GerritConfigurationProvider;
import eu.venthe.pipeline.orchestrator.plugins.gerrit.utils.GerritHeaders;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@RequiredArgsConstructor
@Service
public class GerritProjectProvider implements ProjectProvider {
    private final GerritApi gerritApi;
    private final ObjectMapper objectMapper;
    private final GerritConfigurationProvider gerritConfigurationProvider;
    private final GerritHeaders gerritHeaders;

    @SneakyThrows
    @Override
    public Collection<String> getProjects() {
        return gerritConfigurationProvider.getAllConfigurations().stream()
                .flatMap(configuration -> {
                    HttpEntity<?> headers = gerritHeaders.prepareHeaders(configuration);

                    JsonNode config = gerritApi.getConfig().getConfig(configuration, headers);

                    List<String> objects = new ArrayList<>();
                    Map<String, String> collect = config.get("download").get("schemes").properties().stream()
                            .filter(k -> k.getKey().equals("http") || k.getKey().equals("ssh"))
                            .map(e -> Map.entry(e.getKey(), e.getValue().get("url").textValue().replace("${project}", "%s")))
                            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

                    Set<String> webLinks = StreamSupport.stream(gerritApi.getProjects().getProjects(configuration, headers).spliterator(), false)
                            .map(node -> {
                                ObjectNode newNode = node.deepCopy();
                                // newNode.remove("web_links");
                                String id = newNode.get("id").textValue();
                                newNode.set("ssh", objectMapper.getNodeFactory().textNode(collect.get("ssh").formatted(id)));
                                newNode.set("http", objectMapper.getNodeFactory().textNode(collect.get("http").formatted(id)));
                                return newNode;
                            })
                            .map(JsonNode::toPrettyString)
                            .collect(Collectors.toSet());
                    objects.addAll(webLinks);

                    return objects.stream();
                })
                .collect(Collectors.toList());
    }
}
